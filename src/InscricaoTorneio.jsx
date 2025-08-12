import React, { useEffect, useState } from 'react';
import api from './api';

export default function InscricaoTorneio({ treinadorId }) {
    const [torneios, setTorneios] = useState([]);
    const [mensagem, setMensagem] = useState('');
    const [equipaId, setEquipaId] = useState(null);

    useEffect(() => {
        carregarEquipa();
    }, []);


    useEffect(() => {
        if (equipaId) carregarTorneios();
    }, [equipaId]);


    const carregarEquipa = async () => {
        try {
            const res = await api.get(`/treinadores/${treinadorId}/team`);
            setEquipaId(res.data.id);
        } catch (error) {
            console.error("Erro ao carregar equipa:", error);
            setMensagem('Não foi possível obter a equipa. Verifica se já foi criada.');
        }
    }

    const carregarTorneios = async () => {
        try {
            const res = await api.get(`/torneios/torneiosDisponiveis/${equipaId}`);
            setTorneios(res.data);
        } catch (error) {
            setMensagem(error.response?.data || 'Erro ao inscrever equipa.');
        }
    };

    const inscreverEquipa = async (torneioId) => {
        try {
            await api.post(`/torneios/${torneioId}/inscrever`, JSON.stringify(equipaId), {
                headers: {
                    'Content-Type': 'application/json'
                }});
            setMensagem('Equipa inscrita com sucesso!');
            // Remove o torneio da lista (opcional)
            setTorneios(prev => prev.filter(t => t.id !== torneioId));
        } catch (error) {
            setMensagem('Erro ao inscrever equipa.');
            console.error(error);
        }
    };

    return (
        <div className="bg-white p-6 rounded shadow-md mt-8">
            <h2 className="text-2xl font-bold mb-4">🎯 Inscrição em Torneios</h2>
            <h2 className="text-xl font-semibold mb-4">Obs : Apenas é possivel inscrever uma equipa no torneio se existir pelo menos um jogador que jogue o jogo do torneio</h2>
            {mensagem && <p className="text-green-600 mb-4">{mensagem}</p>}

            {torneios.length > 0 ? (
                <ul className="space-y-4">
                    {torneios.map(t => (
                        <li key={t.id} className="border p-4 rounded flex justify-between items-center">
                            <div>
                                <h3 className="text-lg font-semibold">{t.nome}</h3>
                                <p className="text-sm text-gray-600">Jogo: {t.game}</p>
                            </div>
                            <button
                                onClick={() => inscreverEquipa(t.id)}
                                className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
                            >
                                Inscrever
                            </button>
                        </li>
                    ))}
                </ul>
            ) : (
                <p className="text-gray-600">Nenhum torneio disponível no momento.</p>
            )}
        </div>
    );
}
