import React, { useState, useEffect } from 'react';
import axios from 'axios';

export default function UtilizadoresAdmin() {
    const [utilizadores, setUtilizadores] = useState([]);
    const [novoUtilizador, setNovoUtilizador] = useState({
        nome: '',
        email: '',
        username: '',
        password: '',
        role: 'PLAYER',
        game: 'FPS',
        posicao: ''
    });

    useEffect(() => {
        carregarUtilizadores();
    }, []);

    const carregarUtilizadores = async () => {
        try {
            const res = await axios.get('http://localhost:8080/api/admin/utilizadores');
            setUtilizadores(res.data);
        } catch (err) {
            console.error('Erro ao carregar utilizadores', err);
        }
    };

    const adicionarUtilizador = async () => {
        try {
            const res = await axios.post('http://localhost:8080/api/admin/utilizadores', novoUtilizador);

            // adiciona o novo utilizador ao estado local
            const criado = res.data;
            setUtilizadores((prev) => [...prev, criado]);

            // limpa o formulário
            setNovoUtilizador({
                nome: '',
                email: '',
                username: '',
                password: '',
                role: 'PLAYER',
                game: 'FPS',
                posicao: ''
            });
        } catch (err) {
            console.error('Erro ao adicionar utilizador', err);
        }
    };

    const removerUtilizador = async (id) => {
        try {
            await axios.delete(`http://localhost:8080/api/admin/utilizadores/${id}`);

            // remove do estado local sem recarregar toda a lista
            setUtilizadores((prev) => prev.filter((u) => u.id !== id));
        } catch (err) {
            console.error('Erro ao remover utilizador', err);
        }
    };

    return (
        <div className="space-y-8">
            <h1 className="text-2xl font-bold text-gray-800">Gestão de Utilizadores</h1>

            {/* Formulário de criação */}
            <div className="bg-white p-6 rounded shadow">
                <h2 className="text-lg font-semibold mb-4">Criar Novo Utilizador</h2>
                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                    <input
                        type="text"
                        placeholder="Nome"
                        className="border p-2 rounded"
                        value={novoUtilizador.nome}
                        onChange={(e) => setNovoUtilizador({ ...novoUtilizador, nome: e.target.value })}
                    />
                    <input
                        type="email"
                        placeholder="Email"
                        className="border p-2 rounded"
                        value={novoUtilizador.email}
                        onChange={(e) => setNovoUtilizador({ ...novoUtilizador, email: e.target.value })}
                    />
                    <input
                        type="text"
                        placeholder="Username"
                        className="border p-2 rounded"
                        value={novoUtilizador.username}
                        onChange={(e) => setNovoUtilizador({ ...novoUtilizador, username: e.target.value })}
                    />
                    <input
                        type="password"
                        placeholder="Password"
                        className="border p-2 rounded"
                        value={novoUtilizador.password}
                        onChange={(e) => setNovoUtilizador({ ...novoUtilizador, password: e.target.value })}
                    />
                    <select
                        className="border p-2 rounded"
                        value={novoUtilizador.role}
                        onChange={(e) => setNovoUtilizador({ ...novoUtilizador, role: e.target.value })}
                    >
                        <option value="PLAYER">Jogador</option>
                        <option value="TREINADOR">Treinador</option>
                    </select>

                    {novoUtilizador.role === 'PLAYER' && (
                        <>
                            <div>
                                <label className="block mb-1 text-sm text-gray-700">Jogo</label>
                                <select
                                    className="border p-2 rounded w-full"
                                    value={novoUtilizador.game || ''}
                                    onChange={(e) => setNovoUtilizador({ ...novoUtilizador, game: e.target.value })}
                                >
                                    <option value="">-- Escolha o jogo --</option>
                                    <option value="FPS">FPS</option>
                                    <option value="MOBA">MOBA</option>
                                    <option value="EFOOTBALL">eFootball</option>
                                </select>
                            </div>

                            {novoUtilizador.game === 'EFOOTBALL' && (
                                <div>
                                    <label className="block mb-1 text-sm text-gray-700">Posição</label>
                                    <select
                                        className="border p-2 rounded w-full"
                                        value={novoUtilizador.posicao || ''}
                                        onChange={(e) => setNovoUtilizador({ ...novoUtilizador, posicao: e.target.value })}
                                    >
                                        <option value="">-- Escolha a posição --</option>
                                        <option value="GOALKEEPER">GOALKEEPER</option>
                                        <option value="DEFENDER">DEFENDER</option>
                                        <option value="MIDFIELDER">MIDFIELDER</option>
                                        <option value="FORWARD">FORWARD</option>
                                    </select>
                                </div>
                            )}
                        </>
                    )}
                </div>
                <button
                    onClick={adicionarUtilizador}
                    className="mt-4 px-4 py-2 bg-indigo-600 text-white rounded hover:bg-indigo-700"
                >
                    Criar Utilizador
                </button>
            </div>

            {/* Lista de utilizadores */}
            <div className="bg-white p-6 rounded shadow">
                <h2 className="text-lg font-semibold mb-4">Lista de Utilizadores</h2>
                <table className="w-full text-left">
                    <thead>
                    <tr className="border-b font-semibold text-gray-700">
                        <th className="py-2">Nome</th>
                        <th>Email</th>
                        <th>Username</th>
                        <th>Role</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    {utilizadores.map((u) => (
                        <tr key={u.id} className="border-b">
                            <td className="py-2">{u.nome}</td>
                            <td>{u.email}</td>
                            <td>{u.username}</td>
                            <td>{u.role}</td>
                            <td>
                                <button
                                    onClick={() => removerUtilizador(u.id)}
                                    className="text-red-500 hover:text-red-700"
                                >
                                    Remover
                                </button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
}
