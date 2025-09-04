import React, { useEffect, useState } from 'react';
import api from './api';

export default function Torneios({ jogadorId }) {
    const [equipaId, setEquipaId] = useState(null);
    const [torneios, setTorneios] = useState([]);
    const [torneioSelecionado, setTorneioSelecionado] = useState(null);
    const [partidas, setPartidas] = useState([]);
    const [classificacao, setClassificacao] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        if (jogadorId) {
            carregarEquipa();
        }
    }, [jogadorId]);

    const carregarEquipa = async () => {
        try {
            const res = await api.get(`/players/${jogadorId}/equipa`);
            if (res.data) {
                setEquipaId(res.data.id);
                carregarTorneios(res.data);
            }
        } catch (err) {
            console.error("Erro ao carregar equipa:", err);
        }
    };

    const carregarTorneios = async () => {
        setLoading(true);
        try {
            const res = await api.get(`/players/${jogadorId}/torneios`);
            setTorneios(res.data);
        } catch (err) {
            console.error("Erro ao carregar torneios:", err);
        } finally {
            setLoading(false);
        }
    };


    const verDetalhesTorneio = async (torneioId) => {
        try {
            setTorneioSelecionado(torneioId);

            const partidasRes = await api.get(`/players/${jogadorId}/torneios/${torneioId}/partidas`);
            setPartidas(partidasRes.data);

            const classRes = await api.get(`/torneios/${torneioId}/classificacao`);
            setClassificacao(classRes.data);
        } catch (err) {
            console.error("Erro ao carregar detalhes do torneio:", err);
        }
    };

    if (loading) return <p>Carregando torneios...</p>;

    return (
        <div className="max-w-4xl mx-auto p-6 bg-white rounded shadow">
            <h2 className="text-2xl font-bold mb-4">🏆 Torneios em que a minha equipa participa</h2>

            {torneios.length === 0 && <p>Não está inscrito em nenhum torneio.</p>}

            <div className="grid gap-4">
                {torneios.map((t) => (
                    <div
                        key={t.id}
                        className={`p-4 border rounded cursor-pointer hover:bg-gray-100 ${
                            torneioSelecionado === t.id ? "border-blue-500" : ""
                        }`}
                        onClick={() => verDetalhesTorneio(t.id)}
                    >
                        <h3 className="font-bold text-lg">{t.nome}</h3>
                        <p className="text-sm text-gray-600">
                            Jogo: {t.game || "N/A"}
                        </p>
                    </div>
                ))}
            </div>

            {torneioSelecionado && (
                <div className="mt-6">
                    <h3 className="text-xl font-bold">📅 Partidas onde participei</h3>
                    {partidas.length === 0 ? (
                        <p>Sem partidas registadas.</p>
                    ) : (
                        <ul className="list-disc pl-6">
                            {partidas.map((p) => (
                                <li key={p.id}>
                                    Resultado : {p.resultado || "Por jogar"} - {p.nomeEquipa1} vs {p.nomeEquipa2} — {p.data}
                                </li>
                            ))}
                        </ul>
                    )}

                    <h3 className="text-xl font-bold mt-4">📊 Classificação</h3>
                    {classificacao.length === 0 ? (
                        <p>Classificação ainda não disponível.</p>
                    ) : (
                        <table className="w-full mt-2 border">
                            <thead>
                            <tr className="bg-gray-200">
                                <th className="p-2 border">Posição</th>
                                <th className="p-2 border">Equipa</th>
                                <th className="p-2 border">Pontos</th>
                            </tr>
                            </thead>
                            <tbody>
                            {classificacao.map((c, idx) => (
                                <tr key={idx}>
                                    <td className="p-2 border">{idx + 1}</td>
                                    <td className="p-2 border">{c.nome}</td>
                                    <td className="p-2 border">{c.pontos}</td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    )}
                </div>
            )}
        </div>
    );
}