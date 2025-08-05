import React from 'react';
import {
  LineChart,
  Line,
  BarChart,
  Bar,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
  ResponsiveContainer,
  RadarChart,
  PolarGrid,
  PolarAngleAxis,
  PolarRadiusAxis,
  Radar
} from 'recharts';
import logoproad from '../assets/proad.png';
import './Dashboard.css';

// Dados para o gráfico de linha principal
const lineData = [
  { x: 1, PV: 250, UV: 150 },
  { x: 2, PV: 400, UV: 200 },
  { x: 3, PV: 450, UV: -100 },
  { x: 4, PV: 350, UV: 180 },
  { x: 5, PV: 300, UV: -50 },
  { x: 6, PV: 250, UV: 120 },
  { x: 7, PV: 200, UV: 250 },
  { x: 8, PV: 180, UV: 200 },
  { x: 9, PV: 50, UV: 280 },
  { x: 10, PV: 30, UV: -80 },
  { x: 11, PV: 20, UV: 50 },
  { x: 12, PV: 80, UV: -150 },
  { x: 13, PV: 150, UV: 100 },
  { x: 14, PV: 120, UV: -200 },
  { x: 15, PV: 90, UV: -100 },
  { x: 16, PV: 160, UV: 80 },
  { x: 17, PV: 900, UV: 350 },
  { x: 18, PV: 600, UV: 200 },
  { x: 19, PV: 480, UV: 150 },
  { x: 20, PV: 520, UV: 180 },
  { x: 21, PV: 280, UV: 120 },
  { x: 22, PV: 450, UV: 200 },
  { x: 23, PV: 380, UV: -200 },
  { x: 24, PV: 420, UV: 100 },
  { x: 25, PV: 200, UV: 250 },
  { x: 26, PV: 180, UV: -150 },
  { x: 27, PV: 380, UV: 180 },
  { x: 28, PV: 320, UV: 150 },
  { x: 29, PV: 280, UV: -300 },
  { x: 30, PV: 250, UV: 120 },
  { x: 31, PV: 200, UV: -200 },
  { x: 32, PV: 180, UV: 100 },
  { x: 33, PV: 350, UV: 180 },
  { x: 34, PV: 280, UV: 150 },
  { x: 35, PV: 200, UV: -100 },
  { x: 36, PV: 180, UV: 80 },
  { x: 37, PV: 450, UV: 200 },
  { x: 38, PV: 150, UV: 120 },
  { x: 39, PV: 200, UV: 100 },
  { x: 40, PV: 180, UV: 80 }
];

// Dados para o primeiro gráfico de barras
const barData1 = [
  { name: 'Page A', value: 250, line: 300 },
  { name: 'Page B', value: 320, line: 380 },
  { name: 'Page C', value: 580, line: 650 },
  { name: 'Page D', value: 600, line: 680 },
  { name: 'Page E', value: 590, line: 620 },
  { name: 'Page F', value: 520, line: 580 }
];

// Dados para o segundo gráfico de barras
const barData2 = [
  { name: 'Cat 1', series1: 2500, series2: 3500, series3: 200 },
  { name: 'Cat 2', series1: 1200, series2: 3000, series3: 180 },
  { name: 'Cat 3', series1: 800, series2: 1500, series3: 150 },
  { name: 'Cat 4', series1: 3500, series2: 2800, series3: 320 },
  { name: 'Cat 5', series1: 4200, series2: 2500, series3: 280 },
  { name: 'Cat 6', series1: 3800, series2: 3200, series3: 250 }
];

// Dados para o gráfico radar
const radarData = [
  { subject: 'Math', A: 120, fullMark: 150 },
  { subject: 'Chinese', A: 98, fullMark: 150 },
  { subject: 'English', A: 86, fullMark: 150 },
  { subject: 'Geography', A: 99, fullMark: 150 },
  { subject: 'Physics', A: 85, fullMark: 150 },
  { subject: 'History', A: 65, fullMark: 150 }
];

const Dashboard = () => {
  return (
    <div className="dashboard-container">
      {/* Header */}
      <header className="dashboard-header">
        <div className="header-wrapper">
          <div className="header-content">
            <div className="logo-section">
              <div className="logo-icon">
                <img src={logoproad} alt="PROAD Logo" className="logo-image" />
              </div>
              <span className="brand-name-dashboard">Proad</span>
            </div>
            <div className="nav-section">
                <a href="/dashboard" className="nav-button nav-button-primary">Dashboard</a>
                <a href="/login" className="nav-button nav-button-secondary">Painel Administrativo</a>

            </div>
          </div>
        </div>
      </header>

      <div className="dashboard-main">
        {/* Cards Indicativos */}
        <div className="cards-grid">
          <div className="indicator-card card-blue-600">
            <h3 className="card-title">Card indicativo</h3>
            <p className="card-value">35,02%</p>
            <p className="card-metric">Métrica X</p>
            <div className="card-tag card-tag-blue">
              Tag X
            </div>
          </div>
          
          <div className="indicator-card card-orange-500">
            <h3 className="card-title">Card indicativo</h3>
            <p className="card-value">35,02%</p>
            <p className="card-metric">Métrica X</p>
            <div className="card-tag card-tag-orange">
              Tag X
            </div>
          </div>
          
          <div className="indicator-card card-blue-500">
            <h3 className="card-title">Card indicativo</h3>
            <p className="card-value">35,02%</p>
            <p className="card-metric">Métrica X</p>
            <div className="card-tag card-tag-blue">
              Tag X
            </div>
          </div>
          
          <div className="indicator-card card-orange-400">
            <h3 className="card-title">Card Indicativo</h3>
            <p className="card-value">35,02%</p>
            <p className="card-metric">Métrica X</p>
            <div className="card-tag card-tag-orange">
              Tag X
            </div>
          </div>
        </div>

        {/* Gráfico de Linha Principal */}
        <div className="chart-container">
          <ResponsiveContainer width="100%" height={400}>
            <LineChart data={lineData}>
              <CartesianGrid strokeDasharray="3 3" stroke="#f0f0f0" />
              <XAxis 
                dataKey="x" 
                axisLine={false}
                tickLine={false}
                tick={{ fontSize: 12, fill: '#666' }}
              />
              <YAxis 
                axisLine={false}
                tickLine={false}
                tick={{ fontSize: 12, fill: '#666' }}
                domain={[-500, 1000]}
              />
              <Tooltip />
              <Legend />
              <Bar dataKey="UV" fill="#4ade80" name="UV" />
              <Bar dataKey="PV" fill="#6366f1" name="PV" />
            </LineChart>
          </ResponsiveContainer>
        </div>

        {/* Gráficos Exemplo */}
        <div className="charts-grid">
          {/* Primeiro Gráfico Exemplo */}
          <div className="chart-container">
            <h3 className="chart-title">Gráfico Exemplo</h3>
            <ResponsiveContainer width="100%" height={300}>
              <BarChart data={barData1}>
                <CartesianGrid strokeDasharray="3 3" stroke="#f0f0f0" />
                <XAxis 
                  dataKey="name" 
                  axisLine={false}
                  tickLine={false}
                  tick={{ fontSize: 12, fill: '#666' }}
                />
                <YAxis 
                  axisLine={false}
                  tickLine={false}
                  tick={{ fontSize: 12, fill: '#666' }}
                />
                <Tooltip />
                <Bar dataKey="value" fill="#6366f1" />
                <Line 
                  type="monotone" 
                  dataKey="line" 
                  stroke="#f59e0b" 
                  strokeWidth={2}
                  dot={{ fill: '#f59e0b', strokeWidth: 2, r: 4 }}
                />
              </BarChart>
            </ResponsiveContainer>
            <p className="chart-description">
              Texto descritivo do gráfico. Utilizado https://recharts.org/
            </p>
          </div>

          {/* Segundo Gráfico Exemplo */}
          <div className="chart-container">
            <h3 className="chart-title">Gráfico Exemplo</h3>
            <ResponsiveContainer width="100%" height={300}>
              <BarChart data={barData2}>
                <CartesianGrid strokeDasharray="3 3" stroke="#f0f0f0" />
                <XAxis 
                  dataKey="name" 
                  axisLine={false}
                  tickLine={false}
                  tick={{ fontSize: 12, fill: '#666' }}
                />
                <YAxis 
                  axisLine={false}
                  tickLine={false}
                  tick={{ fontSize: 12, fill: '#666' }}
                />
                <Tooltip />
                <Bar dataKey="series1" fill="#6366f1" />
                <Bar dataKey="series2" fill="#4ade80" />
                <Line 
                  type="monotone" 
                  dataKey="series3" 
                  stroke="#8b5cf6" 
                  strokeWidth={2}
                  dot={{ fill: '#8b5cf6', strokeWidth: 2, r: 4 }}
                />
              </BarChart>
            </ResponsiveContainer>
            <p className="chart-description">
              Texto descritivo do gráfico. Utilizado https://recharts.org/
            </p>
          </div>
        </div>

        {/* Gráfico Radar */}
        <div className="chart-container">
          <div className="radar-container">
            <div className="radar-wrapper">
              <ResponsiveContainer width="100%" height="100%">
                <RadarChart data={radarData}>
                  <PolarGrid stroke="#e5e7eb" />
                  <PolarAngleAxis 
                    dataKey="subject" 
                    tick={{ fontSize: 12, fill: '#666' }}
                  />
                  <PolarRadiusAxis 
                    angle={90} 
                    domain={[0, 150]} 
                    tick={{ fontSize: 10, fill: '#666' }}
                  />
                  <Radar
                    name="Performance"
                    dataKey="A"
                    stroke="#8b5cf6"
                    fill="#8b5cf6"
                    fillOpacity={0.3}
                    strokeWidth={2}
                  />
                </RadarChart>
              </ResponsiveContainer>
            </div>
          </div>
          <p className="chart-description radar-description">
            Texto descritivo do gráfico. Utilizado https://recharts.org/
          </p>
        </div>
      </div>

      {/* Footer */}
      <footer className="dashboard-footer">
        <div className="footer-content">
          <p className="footer-text">PROAD © 2017/2025 - Todos os direitos reservados</p>
        </div>
      </footer>
    </div>
  );
};

export default Dashboard;