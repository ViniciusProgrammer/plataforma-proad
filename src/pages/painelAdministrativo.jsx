import React, { useState } from 'react';
import logoproad from '../assets/proad.png';

const PainelAdministrativo = () => {
  const [formData, setFormData] = useState({
    processNumber: '',
    processType: '',
    object: ''
  });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = () => {
    if (!formData.processNumber || !formData.processType || !formData.object) {
      alert('Por favor, preencha todos os campos obrigatórios.');
      return;
    }
    console.log('Dados do formulário:', formData);
    alert('Processo cadastrado com sucesso!');
  };

  const handleCancel = () => {
    setFormData({
      processNumber: '',
      processType: '',
      object: ''
    });
  };

  return (
    <div style={styles.container}>
      {/* Header */}
      <header style={styles.header}>
        <div style={styles.headerContent}>
          <div style={styles.logo}>
            <img src={logoproad} alt="Proad Logo" style={styles.logoImage} />
            <span style={styles.logoText}>Proad</span>
          </div>
          <nav style={styles.nav}>
            <a href="/dashboard" style={styles.navLink}>Dashboard</a>
            <a href="/login" style={{...styles.navLink, ...styles.navLinkActive}}>Painel Administrativo</a>
          </nav>
        </div>
      </header>

      {/* Main Content */}
      <main style={styles.main}>
        <div style={styles.content}>
          <div style={styles.titleSection}>
            <h1 style={styles.title}>Cadastro de Processo</h1>
            <p style={styles.subtitle}>Preencha as informações para cadastrar um novo processo no sistema</p>
          </div>

          <div style={styles.form}>
            <div style={styles.formSection}>
              <h2 style={styles.sectionTitle}>Informações Básicas</h2>
              
              <div style={styles.formRow}>
                <div style={styles.formGroup}>
                  <div style={styles.label}>
                    Número do Processo <span style={styles.required}>*</span>
                  </div>
                  <input
                    type="text"
                    name="processNumber"
                    value={formData.processNumber}
                    onChange={handleInputChange}
                    placeholder="Ex: 2024.001.001"
                    style={styles.input}
                  />
                </div>
                
                <div style={styles.formGroup}>
                  <div style={styles.label}>
                    Tipo de Processo <span style={styles.required}>*</span>
                  </div>
                  <select
                    name="processType"
                    value={formData.processType}
                    onChange={handleInputChange}
                    style={styles.select}
                  >
                    <option value="">Selecione o tipo</option>
                    <option value="licitacao">Licitação</option>
                    <option value="contrato">Contrato</option>
                    <option value="administrativo">Administrativo</option>
                    <option value="juridico">Jurídico</option>
                    <option value="financeiro">Financeiro</option>
                  </select>
                </div>
              </div>

              <div style={styles.formGroup}>
                <div style={styles.label}>
                  Objeto <span style={styles.required}>*</span>
                </div>
                <textarea
                  name="object"
                  value={formData.object}
                  onChange={handleInputChange}
                  placeholder="Descreva o objeto do processo..."
                  style={styles.textarea}
                  rows="5"
                />
              </div>
            </div>

            <div style={styles.formActions}>
              <button type="button" onClick={handleCancel} style={styles.cancelButton}>
                Cancelar
              </button>
              <button type="button" onClick={handleSubmit} style={styles.submitButton}>
                Cadastrar Processo
              </button>
            </div>
          </div>
        </div>
      </main>

      {/* Footer */}
      <footer style={styles.footer}>
        <p>PROAD © 2017/2025 - Todos os direitos reservados</p>
      </footer>
    </div>
  );
};

const styles = {
  container: {
    minHeight: '100vh',
    backgroundColor: '#f8f9fa',
    fontFamily: '-apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif',
    display: 'flex',
    flexDirection: 'column'
  },
  header: {
    backgroundColor: '#ffffff',
    borderBottom: '1px solid #e9ecef',
    padding: '0 2rem'
  },
  headerContent: {
    maxWidth: '1200px',
    margin: '0 auto',
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
    height: '64px'
  },
  logo: {
    display: 'flex',
    alignItems: 'center',
    gap: '12px'
  },
  logoImage: {
    height: '32px',
    width: 'auto',
    objectFit: 'contain'
  },
  logoText: {
    fontSize: '1.5rem',
    fontWeight: '600',
    color: '#2c3e50'
  },
  nav: {
    display: 'flex',
    gap: '2rem'
  },
  navLink: {
    color: '#6c757d',
    textDecoration: 'none',
    fontSize: '1rem',
    fontWeight: '500',
    transition: 'color 0.2s ease'
  },
  navLinkActive: {
    color: '#2980b9'
  },
  main: {
    flex: 1,
    padding: '2rem'
  },
  content: {
    maxWidth: '1200px',
    margin: '0 auto'
  },
  titleSection: {
    marginBottom: '2rem'
  },
  title: {
    fontSize: '2rem',
    fontWeight: '700',
    color: '#2c3e50',
    margin: '0 0 0.5rem 0'
  },
  subtitle: {
    fontSize: '1rem',
    color: '#6c757d',
    margin: 0
  },
  form: {
    backgroundColor: '#ffffff',
    borderRadius: '8px',
    padding: '2rem',
    boxShadow: '0 2px 4px rgba(0, 0, 0, 0.1)'
  },
  formSection: {
    marginBottom: '2rem'
  },
  sectionTitle: {
    fontSize: '1.25rem',
    fontWeight: '600',
    color: '#2c3e50',
    marginBottom: '1.5rem'
  },
  formRow: {
    display: 'grid',
    gridTemplateColumns: '1fr 1fr',
    gap: '1.5rem',
    marginBottom: '1.5rem'
  },
  formGroup: {
    display: 'flex',
    flexDirection: 'column'
  },
  label: {
    fontSize: '0.875rem',
    fontWeight: '600',
    color: '#495057',
    marginBottom: '0.5rem'
  },
  required: {
    color: '#dc3545'
  },
  input: {
    color: '#495057',
    padding: '0.75rem',
    fontSize: '1rem',
    border: '1px solid #ced4da',
    backgroundColor: '#ffffff',
    borderRadius: '4px',
    transition: 'border-color 0.2s ease, box-shadow 0.2s ease',
    outline: 'none'
  },
  select: {
    padding: '0.75rem',
    fontSize: '1rem',
    border: '1px solid #ced4da',
    borderRadius: '4px',
    backgroundColor: '#ffffff',
    color: '#495057',
    transition: 'border-color 0.2s ease, box-shadow 0.2s ease',
    outline: 'none'
  },
  textarea: {
    color: '#495057',
    padding: '0.75rem',
    fontSize: '1rem',
    border: '1px solid #ced4da',
    borderRadius: '4px',
    backgroundColor: '#ffffff',
    resize: 'vertical',
    fontFamily: 'inherit',
    transition: 'border-color 0.2s ease, box-shadow 0.2s ease',
    outline: 'none'
  },
  formActions: {
    display: 'flex',
    justifyContent: 'flex-end',
    gap: '1rem',
    paddingTop: '1rem',
    borderTop: '1px solid #e9ecef'
  },
  cancelButton: {
    padding: '0.75rem 1.5rem',
    fontSize: '1rem',
    fontWeight: '500',
    color: '#6c757d',
    backgroundColor: '#ffffff',
    border: '1px solid #ced4da',
    borderRadius: '4px',
    cursor: 'pointer',
    transition: 'all 0.2s ease'
  },
  submitButton: {
    padding: '0.75rem 1.5rem',
    fontSize: '1rem',
    fontWeight: '500',
    color: '#ffffff',
    backgroundColor: '#2980b9',
    border: 'none',
    borderRadius: '4px',
    cursor: 'pointer',
    transition: 'background-color 0.2s ease'
  },
  footer: {
    backgroundColor: '#2980b9',
    color: '#ffffff',
    textAlign: 'center',
    padding: '1rem 2rem'
  }
};

// Adicionar estilos de hover e focus via CSS-in-JS
const styleSheet = document.createElement('style');
styleSheet.textContent = `
  input:focus, select:focus, textarea:focus {
    border-color: #2980b9 !important;
    box-shadow: 0 0 0 0.2rem rgba(41, 128, 185, 0.25) !important;
  }
  
  button:hover {
    transform: translateY(-1px);
  }
  
  .cancel-button:hover {
    background-color: #f8f9fa !important;
    border-color: #adb5bd !important;
  }
  
  .submit-button:hover {
    background-color: #1f618d !important;
  }
  
  @media (max-width: 768px) {
    .form-row {
      grid-template-columns: 1fr !important;
    }
    
    .header-content {
      flex-direction: column !important;
      height: auto !important;
      padding: 1rem 0 !important;
    }
    
    .nav {
      margin-top: 1rem;
    }
  }
`;
document.head.appendChild(styleSheet);

export default PainelAdministrativo;