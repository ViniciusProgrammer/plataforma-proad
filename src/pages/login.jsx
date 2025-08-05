import { useState } from 'react'
import { useNavigate } from 'react-router-dom'  // importar useNavigate
import './login.css'
import logoproad from '../assets/proad.png'

function Login() {
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const navigate = useNavigate()  // inicializar useNavigate

  const handleSubmit = (e) => {
    e.preventDefault()
    console.log('Login attempt:', { email, password })
    // Aqui você faria a autenticação (ex: chamada API) antes de navegar
    // Por enquanto, só redireciona direto:
    navigate('/administrativo')
  }

  return (
    <div className="fundo-azul">
      <div className="login-container">
        <div className="header">
          <div className="logo-container">
            <div className="logo">
              <img src={logoproad} alt="PROAD Logo" className='logo-proad-login'/>
            </div>
            <span className="brand-name">PROAD</span>
          </div>
        </div>
       
        <div className="login-form-container">
          <h2 className="login-title">Login</h2>
         
          <form onSubmit={handleSubmit} className="login-form">
            <div className="form-group">
              <label htmlFor="email" className="form-label">
                E-mail institucional
              </label>
              <input
                type="email"
                id="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                className="form-input"
                required
              />
            </div>
           
            <div className="form-group">
              <label htmlFor="password" className="form-label">
                Senha
              </label>
              <input
                type="password"
                id="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                className="form-input"
                required
              />
            </div>
           
            <button type="submit" className="login-button">
              Entrar
            </button>
          </form>
        </div>
      </div>
     
      <footer className="footer">
        PROAD © 2017/2025 - Todos os direitos reservados
      </footer>
    </div>
  )
}

export default Login
