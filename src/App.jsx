import { useState } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Login from './Login.jsx';
import Dashboard from './Dashboard.jsx';
import Sidebar from './Sidebar.jsx';
import UtilizadoresAdmin from "./UtilizadoresAdmin.jsx";
import Torneios from "./Torneios.jsx";
import TorneiosAdmin from "./TorneiosAdmin.jsx";
import { useNavigate } from 'react-router-dom';
import EquipaTreinador from "./EquipaTreinador.jsx";
import InscricaoTorneio from "./InscricaoTorneio.jsx";
import ClassificacaoTorneio from "./ClassificacaoTorneio.jsx";
import AgendarPartida from "./AgendarPartida.jsx";
import RegistarResultado from "./RegistarResultado.jsx";
import PerfilJogador from "./PerfilJogador.jsx";

function App() {

    const [utilizador, setUtilizador] = useState(null);
    const handleLogout = () => setUtilizador(null);

    return (
        <Router>
            {utilizador ? (
                <div className="flex">
                    <Sidebar role={utilizador.role} onLogout={handleLogout} />
                    <div className="ml-64 flex-1 p-6">
                        <Routes>
                            <Route path="/dashboard" element={<Dashboard utilizador={utilizador} />} />
                            <Route path="/gestao-utilizadores" element={<UtilizadoresAdmin />} />
                            <Route path="/gestao-torneios" element={<TorneiosAdmin />} />
                            <Route path="/agendar-partida" element={<AgendarPartida />} />
                            <Route path="/registar-resultado" element={<RegistarResultado />} />
                            {utilizador.role === 'TREINADOR' && (
                                <>
                                    <Route path="/equipa" element={<EquipaTreinador treinadorId={utilizador.id} />}/>
                                    <Route path="/inscricoes" element={<InscricaoTorneio treinadorId={utilizador.id} />}/>
                                    <Route path="/classificacao" element={<ClassificacaoTorneio treinadorId={utilizador.id} />} />
                                </>
                            )}
                            {utilizador.role === 'PLAYER' && (
                                <>
                                <Route path="/meu-perfil" element={<PerfilJogador jogadorId={utilizador.id} />} />
                                <Route path="/meus-torneios" element={<Torneios jogadorId={utilizador.id} />} />
                                </>
                            )}
                        </Routes>
                    </div>
                </div>
            ) : (
                <Login onLoginSuccess={setUtilizador} />
            )}
        </Router>
    );
}

export default App;
