import React, { useEffect, useState } from 'react';
import api from './api';

export default function RegistarResultado() {
    const [torneios, setTorneios] = useState([]);
    const [selectedTorneio, setSelectedTorneio] = useState('');
    const [tipoJogo, setTipoJogo] = useState('');
    const [partidas, setPartidas] = useState([]);
    const [partidaId, setPartidaId] = useState('');
    const [resultado, setResultado] = useState('');
    const [jogadores, setJogadores] = useState([]);
    const [estatisticas, setEstatisticas] = useState([]);
    const [mensagem, setMensagem] = useState('');

    useEffect(() => {
        api.get('/admin/torneios').then(res => setTorneios(res.data));
    }, []);

    useEffect(() => {
        if (selectedTorneio) {
            const torneio = torneios.find(t => t.id === selectedTorneio);
            if (torneio) {
                setTipoJogo(torneio.game?.toUpperCase() || ''); // Ex: FPS, MOBA, EFOOTBALL
            }

            api.get(`/torneios/${selectedTorneio}/partidas`).then(res => setPartidas(res.data));
        }
    }, [selectedTorneio]);

    useEffect(() => {
        if (selectedTorneio && partidaId) {
            const partida = partidas.find(p => p.id === partidaId);
            if (!partida) return;

            const equipa1Id = partida.equipe1Id;
            const equipa2Id = partida.equipe2Id;

            Promise.all([
                api.get(`/torneios/${selectedTorneio}/equipas/${equipa1Id}/jogadores-validos`),
                api.get(`/torneios/${selectedTorneio}/equipas/${equipa2Id}/jogadores-validos`)
            ]).then(([res1, res2]) => {
                const allPlayers = [...res1.data, ...res2.data];
                setJogadores(allPlayers);
                setEstatisticas(allPlayers.map(j => ({
                    jogadorId: j.id,
                    tipoJogo: tipoJogo,
                    kills: '',
                    deaths: '',
                    assists: '',
                    golos: '',
                    assistencias: '',
                    posicao: 'MIDFIELDER',
                    precisao: '',
                    headshots: '',
                    personagem: ''
                })));
            });
        }
    }, [partidaId]);

    const handleStatChange = (jogadorId, campo, valor) => {
        setEstatisticas(prev =>
            prev.map(e =>
                e.jogadorId === jogadorId ? { ...e, [campo]: valor } : e
            )
        );
    };

    const registar = async () => {
        if (!partidaId || !resultado) {
            setMensagem('Preenche todos os campos.');
            return;
        }

        const payload = {
            partidaId,
            resultado,
            estatisticasJogadores: estatisticas
        };

        try {
            await api.put(`/admin/partidas/${selectedTorneio}`, payload);
            console.log(partidas)
            setMensagem('Resultado registado com sucesso!');
        } catch (err) {
            console.error(err);
            setMensagem('Erro ao registar resultado.');
        }
    };

    const renderCamposPorTipo = (jogadorId) => {
        switch (tipoJogo) {
            case 'FPS':
                return (
                    <>
                        <input type="number" placeholder="Precisão (%)" onChange={e => handleStatChange(jogadorId, 'precisao', e.target.value)} className="border p-1" />
                        <input type="number" placeholder="Headshots" onChange={e => handleStatChange(jogadorId, 'headshots', e.target.value)} className="border p-1" />
                    </>
                );
            case 'MOBA':
                return (
                    <>
                        <input type="text" placeholder="Personagem" onChange={e => handleStatChange(jogadorId, 'personagem', e.target.value)} className="border p-1" />
                        <input type="number" placeholder="Kills" onChange={e => handleStatChange(jogadorId, 'kills', e.target.value)} className="border p-1" />
                        <input type="number" placeholder="Deaths" onChange={e => handleStatChange(jogadorId, 'deaths', e.target.value)} className="border p-1" />
                        <input type="number" placeholder="Assists" onChange={e => handleStatChange(jogadorId, 'assists', e.target.value)} className="border p-1" />
                    </>
                );
            case 'EFOOTBALL':
                return (
                    <>
                        <select onChange={e => handleStatChange(jogadorId, 'posicao', e.target.value)} className="border p-1">
                            <option value="">Seleciona posição</option>
                            <option value="GOALKEEPER">Goleiro</option>
                            <option value="DEFENDER">Defesa</option>
                            <option value="MIDFIELDER">Médio</option>
                            <option value="FORWARD">Avançado</option>
                        </select>
                        <input
                            type="number"
                            placeholder="Golos"
                            onChange={e => handleStatChange(jogadorId, 'golos', e.target.value)}
                            className="border p-1"
                        />
                        <input
                            type="number"
                            placeholder="Assistências"
                            onChange={e => handleStatChange(jogadorId, 'assistencias', e.target.value)}
                            className="border p-1"
                        />
                    </>
                );
            default:
                return <p className="text-sm text-gray-500">Tipo de jogo não suportado.</p>;
        }
    };

    return (
        <div className="bg-white p-6 rounded shadow-md mt-6">
            <h2 className="text-2xl font-bold mb-4">📝 Registar Resultado</h2>

            {mensagem && <p className="text-red-600 mb-4">{mensagem}</p>}

            <select value={selectedTorneio} onChange={e => setSelectedTorneio(e.target.value)} className="border p-2 w-full mb-4">
                <option value="">Seleciona um torneio</option>
                {torneios.map(t => <option key={t.id} value={t.id}>{t.nome}</option>)}
            </select>

            <select value={partidaId} onChange={e => setPartidaId(e.target.value)} className="border p-2 w-full mb-4">
                <option value="">Seleciona uma partida</option>
                {partidas.map(p => (
                    <option key={p.id} value={p.id}>
                        {p.nomeEquipa1} vs {p.nomeEquipa2} — {new Date(p.data).toLocaleString()}
                    </option>
                ))}
            </select>

            <input
                type="text"
                placeholder="Resultado (ex: 2-1)"
                value={resultado}
                onChange={e => setResultado(e.target.value)}
                className="border p-2 w-full mb-4"
            />

            {jogadores.length > 0 && (
                <>
                    <h3 className="text-lg font-semibold mb-2">📊 Estatísticas por jogador</h3>

                    {jogadores.map(j => (
                        <div key={j.id} className="mb-4 border p-4 rounded">
                            <p className="font-semibold mb-2">{j.nome} ({j.username})</p>
                            <div className="grid grid-cols-2 gap-2">
                                {renderCamposPorTipo(j.id)}
                            </div>
                        </div>
                    ))}
                </>
            )}

            <button onClick={registar} className="bg-green-600 text-white px-4 py-2 rounded">Registar Resultado</button>
        </div>
    );
}
