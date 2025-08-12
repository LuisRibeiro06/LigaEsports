import React, { useEffect, useState } from "react";
import axios from "axios";

export default function TorneiosAdmin() {
    const [torneios, setTorneios] = useState([]);
    const [novoTorneio, setNovoTorneio] = useState({ name: "", game: "FPS" });

    useEffect(() => {
        carregarTorneios();
    }, []);

    async function carregarTorneios() {
        const response = await axios.get("http://localhost:8080/api/admin/torneios");
        setTorneios(response.data);
    }

    async function criarTorneio() {
        if (!novoTorneio.name) return;
        await axios.post("http://localhost:8080/api/admin/torneios", novoTorneio);
        setNovoTorneio({ name: "", game: "FPS" });
        carregarTorneios();
    }

    async function apagarTorneio(id) {
        await axios.delete(`http://localhost:8080/api/admin/torneios/${id}`);
        carregarTorneios();
    }

    return (
        <div className="p-6 bg-white rounded-xl shadow-lg space-y-4">
            <h2 className="text-2xl font-bold text-gray-800">Criar Torneios</h2>

            <div className="flex gap-2">
                <input
                    type="text"
                    value={novoTorneio.name}
                    onChange={e => setNovoTorneio({ ...novoTorneio, name: e.target.value })}
                    placeholder="Nome do torneio"
                    className="border border-gray-300 p-2 rounded w-full"
                />
                <select
                    className="border p-2 rounded w-full"
                    value={novoTorneio.game}
                    onChange={e => setNovoTorneio({ ...novoTorneio, game: e.target.value })}
                >
                    <option value="FPS">FPS</option>
                    <option value="MOBA">MOBA</option>
                    <option value="EFOOTBALL">EFOOTBALL</option>

                </select>
                <button
                    onClick={criarTorneio}
                    className="bg-yellow-400 hover:bg-yellow-500 text-white px-4 py-2 rounded"
                >
                    Criar
                </button>
            </div>

            <ul className="space-y-2">
                {torneios.map(torneio => (
                    <li key={torneio.id} className="flex justify-between items-center border-b pb-1">
                        <span className="text-gray-700">{torneio.name}</span>
                        <span className="text-gray-700 text-sm font-bold mr-2 ml-2 uppercase ">Game : {torneio.game}</span>
                        <button
                            onClick={() => apagarTorneio(torneio.id)}
                            className="text-red-500 hover:text-red-700 text-sm"
                        >
                            Remover
                        </button>
                    </li>
                ))}
            </ul>
        </div>
    );
}
