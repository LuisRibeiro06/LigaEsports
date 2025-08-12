import React, { useEffect, useState } from 'react';
import api from './api';

export default function PerfilJogador({ jogadorId }) {
    const [jogador, setJogador] = useState(null);
    const [equipa, setEquipa] = useState(null);
    const [editMode, setEditMode] = useState(false);
    const [formData, setFormData] = useState({
        name: '',
        email: '',
        username: '',
        precisao: '',
        personagem: '',
        position: ''
    });
    const [mensagem, setMensagem] = useState('');
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        carregarPerfil();
    }, [jogadorId]);

    useEffect(() => {
        if (jogadorId) {
            carregarEquipa();
        }
    }, [jogadorId]);

    const carregarPerfil = async () => {
        try {
            setLoading(true);
            const res = await api.get(`players/${jogadorId}/perfil`);
            console.log(res.data);
            setJogador(res.data);
            setFormData({
                name: res.data.name || '',
                email: res.data.email || '',
                username: res.data.username || '',
                precisao: res.data.precisao || '',
                personagem: res.data.personagem || '',
                position: res.data.position || ''
            });
        } catch (err) {
            console.error("Erro ao carregar perfil:", err);
            setMensagem('Erro ao carregar perfil do jogador.');
        } finally {
            setLoading(false);
        }
    };

    const carregarEquipa = async () => {
        try {
            const res = await api.get(`/players/${jogadorId}/equipa`);
            setEquipa(res.data);
        } catch (err) {
            console.error("Erro ao carregar equipa:", err);
        }
    }

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({
            ...prev,
            [name]: value
        }));
    };

    const salvarPerfil = async () => {
        if (!formData.name || !formData.email || !formData.username) {
            setMensagem('Preenche todos os campos obrigatórios.');
            return;
        }

        try {
            const updateData = {
                name: formData.name,
                email: formData.email,
                username: formData.username
            };

            // Adicionar campos específicos baseados no tipo de jogador
            if (jogador.tipoJogo === 'FPS' && formData.precisao) {
                updateData.precisao = parseFloat(formData.precisao);
            } else if (jogador.tipoJogo === 'MOBA' && formData.personagem) {
                updateData.personagem = formData.personagem;
            } else if (jogador.tipoJogo === 'EFOOTBALL' && formData.position) {
                updateData.position = formData.position;
            }

            await api.put(`players/${jogadorId}/perfil`, updateData);
            setMensagem('Perfil atualizado com sucesso!');
            setEditMode(false);
            await carregarPerfil();
        } catch (err) {
            console.error("Erro ao salvar perfil:", err);
            setMensagem('Erro ao atualizar perfil.');
        }
    };

    const cancelarEdicao = () => {
        setEditMode(false);
        setFormData({
            name: jogador.name || '',
            email: jogador.email || '',
            username: jogador.username || '',
            precisao: jogador.precisao || '',
            personagem: jogador.personagem || '',
            position: jogador.position || ''
        });
        setMensagem('');
    };

    const renderEstatisticasEspecificas = () => {
        if (!jogador) return null;

        switch (jogador.tipoJogo) {
            case 'FPS':
                return (
                    <div className="grid grid-cols-2 gap-4">
                        <div>
                            <span className="font-semibold">Precisão:</span> {jogador.precisao || 0}%
                        </div>
                        <div>
                            <span className="font-semibold">Headshots:</span> {jogador.headshots || 0}
                        </div>
                    </div>
                );
            case 'MOBA':
                return (
                    <div className="grid grid-cols-2 gap-4">
                        <div>
                            <span className="font-semibold">Personagem:</span> {jogador.personagem || 'N/A'}
                        </div>
                        <div>
                            <span className="font-semibold">K/D/A:</span> {jogador.kills || 0}/{jogador.deaths || 0}/{jogador.assists || 0}
                        </div>
                    </div>
                );
            case 'EFOOTBALL':
                return (
                    <div className="grid grid-cols-2 gap-4">
                        <div>
                            <span className="font-semibold">Posição:</span> {jogador.position || 'N/A'}
                        </div>
                        <div>
                            <span className="font-semibold">Golos/Assistências:</span> {jogador.goals || 0}/{jogador.assistencias || 0}
                        </div>
                    </div>
                );
            default:
                return null;
        }
    };

    const renderCamposEspecificos = () => {
        if (!jogador) return null;

        switch (jogador.tipoJogo) {
            case 'FPS':
                return (
                    <div className="mb-4">
                        <label className="block mb-1 font-semibold">Precisão (%):</label>
                        <input
                            type="number"
                            name="precisao"
                            value={formData.precisao}
                            onChange={handleInputChange}
                            className="border p-2 w-full"
                            step="0.1"
                            min="0"
                            max="100"
                        />
                    </div>
                );
            case 'MOBA':
                return (
                    <div className="mb-4">
                        <label className="block mb-1 font-semibold">Personagem Principal:</label>
                        <input
                            type="text"
                            name="personagem"
                            value={formData.personagem}
                            onChange={handleInputChange}
                            className="border p-2 w-full"
                        />
                    </div>
                );
            case 'EFOOTBALL':
                return (
                    <div className="mb-4">
                        <label className="block mb-1 font-semibold">Posição:</label>
                        <select
                            name="position"
                            value={formData.position}
                            onChange={handleInputChange}
                            className="border p-2 w-full"
                        >
                            <option value="">Seleciona uma posição</option>
                            <option value="GOALKEEPER">Guarda-Redes</option>
                            <option value="DEFENDER">Defesa</option>
                            <option value="MIDFIELDER">Médio</option>
                            <option value="FORWARD">Avançado</option>
                        </select>
                    </div>
                );
            default:
                return null;
        }
    };

    if (loading) {
        return <div className="bg-white p-6 rounded shadow-md mt-6">Carregando perfil...</div>;
    }

    if (!jogador) {
        return <div className="bg-white p-6 rounded shadow-md mt-6">Jogador não encontrado.</div>;
    }

    return (
        <div className="bg-white p-6 rounded shadow-md mt-6">
            <div className="flex justify-between items-center mb-4">
                <h2 className="text-2xl font-bold">👤 Perfil do Jogador</h2>
                {!editMode && (
                    <button
                        onClick={() => setEditMode(true)}
                        className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
                    >
                        Editar Perfil
                    </button>
                )}
            </div>

            {mensagem && (
                <p className={`mb-4 ${mensagem.includes('sucesso') ? 'text-green-600' : 'text-red-600'}`}>
                    {mensagem}
                </p>
            )}

            {!editMode ? (
                <div className="space-y-4">
                    <div className="grid grid-cols-2 gap-4">
                        <div>
                            <span className="font-semibold">Nome:</span> {jogador.name}
                        </div>
                        <div>
                            <span className="font-semibold">Username:</span> {jogador.username}
                        </div>
                        <div>
                            <span className="font-semibold">Email:</span> {jogador.email}
                        </div>
                        <div>
                            <span className="font-semibold">Tipo de Jogo:</span> {jogador.tipoJogo}
                        </div>
                        <div>
                            <span className="font-semibold">Jogos Disputados:</span> {jogador.numGames || 0}
                        </div>
                        <div>
                            <span className="font-semibold"> Equipa : </span> {equipa?.nome || 'Carregando...'}
                        </div>
                    </div>

                    <div className="mt-6">
                        <h3 className="text-lg font-semibold mb-3">📊 Estatísticas Específicas</h3>
                        {renderEstatisticasEspecificas()}
                    </div>
                </div>
            ) : (
                <div className="space-y-4">
                    <div className="grid grid-cols-2 gap-4">
                        <div>
                            <label className="block mb-1 font-semibold">Nome *:</label>
                            <input
                                type="text"
                                name="name"
                                value={formData.name}
                                onChange={handleInputChange}
                                className="border p-2 w-full"
                                required
                            />
                        </div>
                        <div>
                            <label className="block mb-1 font-semibold">Username *:</label>
                            <input
                                type="text"
                                name="username"
                                value={formData.username}
                                onChange={handleInputChange}
                                className="border p-2 w-full"
                                required
                            />
                        </div>
                    </div>

                    <div className="mb-4">
                        <label className="block mb-1 font-semibold">Email *:</label>
                        <input
                            type="email"
                            name="email"
                            value={formData.email}
                            onChange={handleInputChange}
                            className="border p-2 w-full"
                            required
                        />
                    </div>

                    {renderCamposEspecificos()}

                    <div className="flex gap-4">
                        <button
                            onClick={salvarPerfil}
                            className="bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700"
                        >
                            Salvar
                        </button>
                        <button
                            onClick={cancelarEdicao}
                            className="bg-gray-600 text-white px-4 py-2 rounded hover:bg-gray-700"
                        >
                            Cancelar
                        </button>
                    </div>
                </div>
            )}
        </div>
    );
}