import React, { useEffect, useState } from 'react';
import {useLocation} from "react-router-dom";
import api from './api';

export default function EquipaTreinador({ treinadorId }) {
    const location = useLocation();
    const [team, setTeam] = useState(null);
    const [novoNome, setNovoNome] = useState('');
    const [jogadores, setJogadores] = useState([]);
    const [selectedJogadorId, setSelectedJogadorId] = useState('');

    useEffect(() => {
        if (treinadorId) {
            carregarEquipa();
        }
    }, [treinadorId, location]);

    const carregarEquipa = async () => {
        try {
            const res = await api.get(`/treinadores/${treinadorId}/team`);
            setTeam(res.data);
            setNovoNome(res.data.nome || '');
            carregarJogadores(res.data.id); // agora aqui usamos o id válido
        } catch {
            setTeam(null);
        }
    };

    const carregarJogadores = async (equipaId) => {
        if (!equipaId) return;

        try {
            const res = await api.get(`/treinadores/${treinadorId}/team/jogadores/sem-equipa`);
            setJogadores(res.data);
        } catch (err) {
            console.error('Erro ao carregar jogadores:', err);
        }
    };

    const criarEquipa = async () => {
        try {

            const res = await api.post(`/treinadores/${treinadorId}/team`, novoNome, {
                headers: { 'Content-Type': 'application/json' }
            });
            console.log('Equipa criada:', res.data);
            await carregarEquipa();
        } catch (err) {
            console.error("Erro ao criar equipa:", err);
        }
    };

    const editarEquipa = async () => {
        try {
            await api.put(`/treinadores/${treinadorId}/team/${team.id}`, {
                nome: novoNome
            }, {
                headers: { 'Content-Type': 'application/json' }
            });
            await carregarEquipa();
        } catch (err) {
            console.error("Erro ao editar equipa:", err);
        }
    };

    const adicionarJogador = async () => {
        if (!selectedJogadorId) {
            alert('Por favor, selecione um jogador.');
            return;
        }
        try {
            await api.post(`/treinadores/${treinadorId}/team/${team.id}/adicionar-jogador/${selectedJogadorId}`);
            setSelectedJogadorId('');
            await carregarEquipa();
        } catch (err) {
            console.error("Erro ao adicionar jogador:", err);
        }
    };

    const removerJogador = async (id) => {
        try {
            await api.delete(`/treinadores/${treinadorId}/team/${team.id}/remover-jogador/${id}`);
            await carregarEquipa();
        } catch (err) {
            console.error("Erro ao remover jogador:", err);
        }
    };

    const renderEquipa = () => (
        <>
            <div className="mb-4">
                <label>Nome da Equipa:</label>
                <input
                    value={novoNome}
                    onChange={e => setNovoNome(e.target.value)}
                    className="border p-2 w-full"
                />
            </div>

            <button
                onClick={editarEquipa}
                className="bg-blue-600 text-white px-4 py-2 rounded mb-4"
            >
                Atualizar Equipa
            </button>

            <h3 className="font-semibold text-lg mb-2">Jogadores:</h3>
            {team.players && team.players.length > 0 ? (
                <ul>
                    {team.players.map(j => (
                        <li key={j.id} className="flex justify-between border-b py-2">
                            {j.nome} ({j.game})
                            <button
                                onClick={() => removerJogador(j.id)}
                                className="text-red-500"
                            >
                                Remover
                            </button>
                        </li>
                    ))}
                </ul>
            ) : (
                <p className="text-gray-600">Ainda não há jogadores na equipa.</p>
            )}

            <div className="mt-4 flex items-center gap-2">
                <select
                    value={selectedJogadorId}
                    onChange={e => setSelectedJogadorId(e.target.value)}
                    className="border p-2 flex-grow"
                >
                    <option value="">Selecione um jogador</option>
                    {jogadores.map(j => (
                        <option key={j.id} value={j.id}>
                            {j.nome} ({j.game})
                        </option>
                    ))}
                </select>
                <button
                    onClick={adicionarJogador}
                    className="bg-green-600 text-white px-4 py-2 rounded"
                >
                    Adicionar Jogador
                </button>
            </div>
        </>
    );

    const renderCriarEquipa = () => (
        <>
            <p>O treinador ainda não tem equipa.</p>
            <div className="mt-4">
                <input
                    value={novoNome}
                    onChange={e => setNovoNome(e.target.value)}
                    placeholder="Nome da equipa"
                    className="border p-2 w-full mb-2"
                />
                <button
                    onClick={criarEquipa}
                    className="bg-green-600 text-white px-4 py-2 rounded"
                >
                    Criar Equipa
                </button>
            </div>
        </>
    );

    return (
        <div className="bg-white p-6 rounded shadow-md">
            <h2 className="text-2xl font-bold mb-4">🧑‍🏫 Gestão da Equipa</h2>
            {team && team.nome ? renderEquipa() : renderCriarEquipa()}
        </div>
    );
}
