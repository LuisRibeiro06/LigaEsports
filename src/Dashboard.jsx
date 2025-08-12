import React from 'react';
import Torneios from './Torneios';
// Importa outros componentes conforme forem implementados futuramente
// import Equipa from './Equipa';
// import InscricaoTorneio from './InscricaoTorneio';
// import TorneiosAdmin from './TorneiosAdmin';

export default function Dashboard({ utilizador }) {
    return (
        <div className="min-h-screen bg-gradient-to-tr from-blue-100 via-white to-white-600 p-6">
            <div className="max-w-7xl mx-auto">
                {/* Cabeçalho de boas-vindas */}
                <div className="bg-white shadow-lg rounded-xl p-6 mb-8">
                    <h1 className="text-4xl font-extrabold text-gray-800 mb-2">
                        👋 Olá, {utilizador.username}
                    </h1>
                    <p className="text-gray-600 text-lg">
                        A tua função no sistema: <span className="font-semibold text-indigo-600">{utilizador.role}</span>
                    </p>
                </div>

                {/* Conteúdo adaptado por tipo de utilizador */}
                {utilizador.role === 'PLAYER' && (
                    <section>
                        <h2 className="text-2xl font-bold text-gray-700 mb-2">🎮 Painel do Jogador</h2>
                        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                        <div className="bg-white rounded-lg shadow p-4">
                            <h3 className="text-lg font-semibold text-gray-800">Gerir Perfil</h3>
                            <p className="text-gray-600">Vê e edita o teu perfil, tal como estatísticas.</p>
                        </div>
                        <div className="bg-white rounded-lg shadow p-4">
                            <h3 className="text-lg font-semibold text-gray-800">Torneios</h3>
                            <p className="text-gray-600">Visualiza os torneios em que estás inscrito.</p>
                        </div>
                        </div>
                    </section>
                )}

                {utilizador.role === 'TREINADOR' && (
                    <section className="space-y-6">
                        <h2 className="text-2xl font-bold text-gray-700 mb-2">🧑‍🏫 Painel do Treinador</h2>
                        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                            <div className="bg-white rounded-lg shadow p-4">
                                <h3 className="text-lg font-semibold text-gray-800">Gerir Equipa</h3>
                                <p className="text-gray-600">Adiciona/remova jogadores, vê estatísticas.</p>
                            </div>
                            <div className="bg-white rounded-lg shadow p-4">
                                <h3 className="text-lg font-semibold text-gray-800">Inscrição em Torneios</h3>
                                <p className="text-gray-600">Escolhe os torneios disponíveis para inscrição.</p>
                            </div>
                            <div className="bg-white rounded-lg shadow p-4">
                                <h3 className="text-lg font-semibold text-gray-800">Classificações e Partidas</h3>
                                <p className="text-gray-600">Acompanha a posição da tua equipa nos torneios.</p>
                            </div>
                        </div>
                    </section>
                )}

                {utilizador.role === 'ADMIN' && (
                    <section className="space-y-6">
                        <h2 className="text-2xl font-bold text-gray-700 mb-2">🛠️ Painel de Administração</h2>
                        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                            <div className="bg-white rounded-lg shadow p-4">
                                <h3 className="text-lg font-semibold text-gray-800">Gestão de Utilizadores</h3>
                                <p className="text-gray-600">Adiciona ou remove jogadores e treinadores.</p>
                            </div>
                            <div className="bg-white rounded-lg shadow p-4">
                                <h3 className="text-lg font-semibold text-gray-800">Criar/Gerir Torneios</h3>
                                <p className="text-gray-600">Cria torneios e configura participantes e regras.</p>
                            </div>
                            <div className="bg-white rounded-lg shadow p-4">
                                <h3 className="text-lg font-semibold text-gray-800">Agendar Partidas</h3>
                                <p className="text-gray-600">Marca jogos entre equipas inscritas.</p>
                            </div>
                            <div className="bg-white rounded-lg shadow p-4">
                                <h3 className="text-lg font-semibold text-gray-800">Atualizar Resultados</h3>
                                <p className="text-gray-600">Regista resultados e atualiza classificações.</p>
                            </div>
                        </div>
                    </section>
                )}
            </div>
        </div>
    );
}
