import { Button } from '@vaadin/react-components/Button.js';
import { Link } from 'react-router-dom';

// El contrato de ruta: Hilla lo leerá automáticamente sin necesidad del tipado estricto
export const config = {
  route: '', 
  title: 'Menú Principal'
};

export default function HomeView() {
  return (
    <div style={{ 
        display: 'flex', 
        flexDirection: 'column', 
        alignItems: 'center', 
        justifyContent: 'center', 
        padding: '3rem', 
        gap: '2rem' 
    }}>
      <h2>Sistema de Gestión</h2>
      <p>Seleccione el módulo con el que desea operar:</p>
      
      <div style={{ display: 'flex', gap: '1rem', flexWrap: 'wrap', justifyContent: 'center' }}>
        <Link to="/FacturaView" tabIndex={-1}>
          <Button theme="primary large">Módulo de Facturas</Button>
        </Link>
        
        <Link to="/TerceroView" tabIndex={-1}>
          <Button theme="primary large">Módulo de Terceros</Button>
        </Link>
        
        <Link to="/FacultadView" tabIndex={-1}>
          <Button theme="primary large">Módulo de Facultades</Button>
        </Link>
      </div>
    </div>
  );
}