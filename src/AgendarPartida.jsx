import React, { useEffect, useState } from 'react';
import api from './api';

export default function AgendarPartida() {
    const [torneios, setTorneios] = useState([]);
    const [selectedTorneio, setSelectedTorneio] = useState('');
    const [equipas, setEquipas] = useState([]);

    const [team1, setTeam1] = useState('');
    const [team2, setTeam2] = useState('');

    const [jogadoresTeam1, setJogadoresTeam1] = useState([]);
    const [jogadoresTeam2, setJogadoresTeam2] = useState([]);
    const [jogadoresSelecionadosTeam1, setJogadoresSelecionadosTeam1] = useState([]);
    const [jogadoresSelecionadosTeam2, setJogadoresSelecionadosTeam2] = useState([]);

    const [data, setData] = useState('');
    const [mensagem, setMensagem] = useState('');

    useEffect(() => {
        api.get('/admin/torneios').then(res => setTorneios(res.data));
    }, []);

    useEffect(() => {
        if (selectedTorneio) {
            api.get(`/torneios/${selectedTorneio}/classificacao`)
                .then(res => setEquipas(res.data.map(e => ({ id: e.teamId, nome: e.nome }))));
        }
    }, [selectedTorneio]);

    const carregarJogadoresValidos = async (equipaId, setJogadores) => {
        if (!selectedTorneio || !equipaId) return;
        try {
            const res = await api.get(`/torneios/${selectedTorneio}/equipas/${equipaId}/jogadores-validos`);
            setJogadores(res.data);
        } catch (err) {
            console.error("Erro ao carregar jogadores válidos:", err);
            setJogadores([]);
        }
    };

    const handleTeam1Change = async (e) => {
        const id = e.target.value;
        setTeam1(id);
        await carregarJogadoresValidos(id, setJogadoresTeam1);
        setJogadoresSelecionadosTeam1([]);
    };

    const handleTeam2Change = async (e) => {
        const id = e.target.value;
        setTeam2(id);
        await carregarJogadoresValidos(id, setJogadoresTeam2);
        setJogadoresSelecionadosTeam2([]);
    };

    const agendar = async () => {
        if (!selectedTorneio || !team1 || !team2 || !data || team1 === team2) {
            setMensagem('Preenche todos os campos corretamente.');
            return;
        }

        if (jogadoresSelecionadosTeam1.length === 0 || jogadoresSelecionadosTeam2.length === 0) {
            setMensagem('Seleciona pelo menos um jogador por equipa.');
            return;
        }

        const partida = {
            equipe1Id: team1,
            equipe2Id: team2,
            data: data,
            jogadoresEquipe1: jogadoresSelecionadosTeam1,
            jogadoresEquipe2: jogadoresSelecionadosTeam2
        };

        try {
            await api.post(`/admin/torneios/${selectedTorneio}/partidas`, partida);
            setMensagem('Partida agendada com sucesso!');
        } catch (err) {
            console.error(err);
            setMensagem('Erro ao agendar partida.');
        }
    };

    return (
        <div className="bg-white p-6 rounded shadow-md mt-6">
            <h2 className="text-2xl font-bold mb-4">📅 Agendar Partida</h2>

            {mensagem && <p className="mb-4 text-red-600">{mensagem}</p>}

            <select value={selectedTorneio} onChange={e => setSelectedTorneio(e.target.value)} className="border p-2 w-full mb-4">
                <option value="">Seleciona um torneio</option>
                {torneios.map(t => <option key={t.id} value={t.id}>{t.name} - {t.game}</option>)}
            </select>

            <div className="grid grid-cols-2 gap-4 mb-4">
                <select value={team1} onChange={handleTeam1Change} className="border p-2">
                    <option value="">Equipa 1</option>
                    {equipas.map(e => <option key={e.id} value={e.id}>{e.nome}</option>)}
                </select>

                <select value={team2} onChange={handleTeam2Change} className="border p-2">
                    <option value="">Equipa 2</option>
                    {equipas.map(e => <option key={e.id} value={e.id}>{e.nome}</option>)}
                </select>
            </div>

            {jogadoresTeam1.length > 0 && (
                <div className="mb-4">
                    <label className="block mb-1 font-semibold">Jogadores que irão participar da Equipa 1:</label>
                    <select
                        multiple
                        value={jogadoresSelecionadosTeam1}
                        onChange={(e) => setJogadoresSelecionadosTeam1(Array.from(e.target.selectedOptions, opt => opt.value))}
                        className="border p-2 w-full h-32"
                    >
                        {jogadoresTeam1.map(j => (
                            <option key={j.id} value={j.id}>{j.nome} ({j.username})</option>
                        ))}
                    </select>
                </div>
            )}

            {jogadoresTeam2.length > 0 && (
                <div className="mb-4">
                    <label className="block mb-1 font-semibold">Jogadores que irão participar da Equipa 2:</label>
                    <select
                        multiple
                        value={jogadoresSelecionadosTeam2}
                        onChange={(e) => setJogadoresSelecionadosTeam2(Array.from(e.target.selectedOptions, opt => opt.value))}
                        className="border p-2 w-full h-32"
                    >
                        {jogadoresTeam2.map(j => (
                            <option key={j.id} value={j.id}>{j.nome} ({j.username})</option>
                        ))}
                    </select>
                </div>
            )}

            <input type="datetime-local" value={data} onChange={e => setData(e.target.value)} className="border p-2 w-full mb-4" />

            <button onClick={agendar} className="bg-blue-600 text-white px-4 py-2 rounded">Agendar</button>
        </div>
    );
}
