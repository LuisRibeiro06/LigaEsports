import React, { useEffect, useState } from 'react';
import api from './api';

export default function ClassificacaoTorneio({ treinadorId }) {
    const [torneios, setTorneios] = useState([]);
    const [equipaId, setEquipaId] = useState(null);
    const [torneioSelecionado, setTorneioSelecionado] = useState(null);
    const [classificacao, setClassificacao] = useState([]);
    const [partidas, setPartidas] = useState([]);
    const [mensagem, setMensagem] = useState('');

    useEffect(() => {
        if (treinadorId) carregarEquipa();
    }, [treinadorId]);

    useEffect(() => {
        if (equipaId) carregarTorneiosDoTreinador();
    }, [equipaId]);

    const carregarEquipa = async () => {
        try {
            const res = await api.get(`/treinadores/${treinadorId}/team`);
            setEquipaId(res.data.id);
        } catch (error) {
            console.error("Erro ao carregar equipa:", error);
            setMensagem("Erro ao carregar a equipa.");
        }
    };

    const carregarTorneiosDoTreinador = async () => {
        try {
            const res = await api.get(`/torneios/equipa/${equipaId}/torneios`);
            setTorneios(res.data);
        } catch (err) {
            console.error("Erro ao carregar torneios:", err);
            setMensagem("Erro ao carregar os torneios da equipa.");
        }
    };

    const carregarDadosDoTorneio = async (torneioId) => {
        try {
            const [classRes, partidasRes] = await Promise.all([
                api.get(`/torneios/${torneioId}/classificacao`),
                api.get(`/torneios/equipa/${equipaId}/partidas`)
            ]);
            setClassificacao(classRes.data);
            setPartidas(partidasRes.data.filter(p => p.torneioId === torneioId));
            setTorneioSelecionado(torneioId);
            setMensagem('');
        } catch (err) {
            console.error("Erro ao carregar dados do torneio:", err);
            setMensagem("Erro ao carregar dados do torneio.");
        }
    };

    const renderMensagem = () =>
        mensagem && <p className="text-red-600 mb-4">{mensagem}</p>;

    const renderPosicao = () => {
        const pos = classificacao.findIndex(e => e.teamId === equipaId);
        return pos >= 0 ? `${pos + 1}º lugar` : 'Equipa não classificada.';
    };

    return (
        <div className="bg-white p-6 rounded shadow-md mt-8">
            <h2 className="text-2xl font-bold mb-4">📊 Acompanhamento de Torneios</h2>
            {renderMensagem()}

            <div className="mb-4">
                <label className="block mb-2">Selecionar Torneio:</label>
                <select
                    className="border p-2 w-full"
                    onChange={e => carregarDadosDoTorneio(e.target.value)}
                    defaultValue=""
                >
                    <option value="" disabled>Escolhe um torneio...</option>
                    {torneios.map(t => (
                        <option key={t.id} value={t.id}>{t.nome}</option>
                    ))}
                </select>
            </div>

            {classificacao.length > 0 && (
                <div className="mb-6">
                    <h3 className="text-lg font-semibold mb-2">Classificação:</h3>
                    <p className="mb-2 font-medium">📌 A tua posição: {renderPosicao()}</p>
                    <ul>
                        {classificacao.map((equipa, index) => (
                            <li key={equipa.teamId} className="border-b py-2">
                                {index + 1}. {equipa.nome} — {equipa.vitorias} vitórias - {equipa.empates} empates - {equipa.derrotas} derrotas -  - {equipa.pontos} pontos
                            </li>
                        ))}
                    </ul>
                </div>
            )}

            {partidas.length > 0 && (
                <div>
                    <h3 className="text-lg font-semibold mb-2">📅 Partidas da Equipa:</h3>
                    <ul>
                        {partidas.map((p, index) => (
                            <li key={p.id} className="border-b py-2">
                                {new Date(p.data).toLocaleDateString()} —
                                {p.equipa1.nome} vs {p.equipa2.nome} →
                                {p.resultado || 'por jogar'}
                            </li>
                        ))}
                    </ul>
                </div>
            )}
        </div>
    );
}
