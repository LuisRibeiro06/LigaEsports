import { useState } from 'react';
import api from './api';
import {useNavigate} from "react-router-dom";

export default function Login({ onLoginSuccess }) {
    const [form, setForm] = useState({ username: '', password: '' });
    const [erro, setErro] = useState('');

    const navigate = useNavigate();
    const handleChange = (e) => {
        setForm({ ...form, [e.target.name]: e.target.value });
    };

    const login = async () => {
        try {
            const res = await api.post('/auth/login', form);
            onLoginSuccess(res.data);
            navigate('/dashboard');
        } catch {
            setErro('Credenciais inválidas.');
        }
    };

    return (
        <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-blue-200 to-white-600">
            <div className="bg-white rounded-2xl shadow-2xl p-10 w-full max-w-sm">
                <h2 className="text-3xl font-bold text-center text-gray-800 mb-6">Iniciar Sessão</h2>

                <div className="mb-4">
                    <label className="block text-gray-600 font-medium mb-2">Username</label>
                    <input
                        type="text"
                        name="username"
                        value={form.username}
                        onChange={handleChange}
                        className="w-full px-4 py-2 rounded-lg border border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500"
                    />
                </div>

                <div className="mb-6">
                    <label className="block text-gray-600 font-medium mb-2">Password</label>
                    <input
                        type="password"
                        name="password"
                        value={form.password}
                        onChange={handleChange}
                        className="w-full px-4 py-2 rounded-lg border border-gray-300 focus:outline-none focus:ring-2 focus:ring-purple-500"
                        placeholder="••••••••"
                    />
                </div>

                {erro && <p className="text-red-500 text-sm mb-4">{erro}</p>}

                <button
                    onClick={login}
                    className="w-full bg-blue-600 hover:bg-blue-700 text-white font-semibold py-2 rounded-lg transition duration-200"
                >
                    Entrar
                </button>
            </div>
        </div>
    );
}
