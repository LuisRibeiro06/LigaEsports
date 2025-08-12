import React from 'react';
import {
    Home,
    Users,
    Trophy,
    Settings,
    LogOut,
    ChartColumnIncreasing,
    BookImage,
    Book,
    BookCheck,
    Gamepad, Gamepad2, CameraIcon
} from 'lucide-react';
import { Link } from 'react-router-dom';

export default function Sidebar({ onLogout, role }) {
    return (
        <div className="h-screen w-64 bg-white shadow-lg fixed top-0 left-0 flex flex-col">
            <div className="p-6 border-b border-gray-200">
                <h1 className="text-2xl font-bold text-gray-800">Liga eSports</h1>
            </div>

            <nav className="flex-1 p-4 space-y-4 text-gray-700">
                <Link to="/dashboard" className="flex items-center space-x-3 hover:text-indigo-600">
                    <Home className="w-5 h-5" />
                    <span>Dashboard</span>
                </Link>

                {role === 'PLAYER' && (
                    <>
                    <Link to="/meu-perfil" className="flex items-center space-x-3 hover:text-indigo-600">
                        <CameraIcon className="w-5 h-5" />
                        <span>Perfil</span>
                    </Link>

                    <Link to="/meus-torneios" className="flex items-center space-x-3 hover:text-indigo-600">
                        <Trophy className="w-5 h-5" />
                        <span>Torneios</span>
                    </Link>
                    </>
                )}

                {role === 'TREINADOR' && (
                    <>
                        <Link to="/equipa" className="flex items-center space-x-3 hover:text-indigo-600">
                            <Users className="w-5 h-5" />
                            <span>Equipa</span>
                        </Link>
                        <Link to="/inscricoes" className="flex items-center space-x-3 hover:text-indigo-600">
                            <Trophy className="w-5 h-5" />
                            <span>Inscrições</span>
                        </Link>
                        <Link to="/classificacao" className="flex items-center space-x-3 hover:text-indigo-600">
                            <ChartColumnIncreasing className="w-5 h-5" />
                            <span>Classificação Torneios</span>
                        </Link>
                    </>
                )}

                {role === 'ADMIN' && (
                    <>
                        <Link to="/gestao-utilizadores" className="flex items-center space-x-3 hover:text-indigo-600">
                            <Users className="w-5 h-5" />
                            <span>Utilizadores</span>
                        </Link>
                        <Link to="/gestao-torneios" className="flex items-center space-x-3 hover:text-indigo-600">
                            <Trophy className="w-5 h-5" />
                            <span>Torneios</span>
                        </Link>
                        <Link to="/agendar-partida" className="flex items-center space-x-3 hover:text-indigo-600">
                            <BookCheck className="w-5 h-5" />
                            <span>Agendar Partida</span>
                        </Link>
                        <Link to="/registar-resultado" className="flex items-center space-x-3 hover:text-indigo-600">
                            <Gamepad2 className="w-5 h-5" />
                            <span>Registar Resultado</span>
                        </Link>
                    </>
                )}
            </nav>

            <div className="p-4 border-t border-gray-200">
                <button
                    onClick={onLogout}
                    className="flex items-center space-x-3 text-red-600 hover:text-red-800"
                >
                    <LogOut className="w-5 h-5" />
                    <span>Sair</span>
                </button>
            </div>
        </div>
    );
}
