
import './App.css'
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'

import Login from './pages/login.jsx'
import Dashboard from './pages/dashboard.jsx'
import PainelAdministrativo from './pages/paineladministrativo.jsx'

function App() {
  return (
    <div className="App">
      <Router>
        <Routes>
          <Route path="/" element={<Dashboard />} />
          <Route path="/login" element={<Login />} />
          <Route path="/dashboard" element={<Dashboard />} />
          <Route path="/administrativo" element={<PainelAdministrativo />} />
        </Routes>
      </Router>
    </div>
  )
}

export default App
