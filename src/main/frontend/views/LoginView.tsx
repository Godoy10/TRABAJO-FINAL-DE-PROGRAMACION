import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { LoginOverlay } from '@vaadin/react-components/LoginOverlay.js';

export const config: ViewConfig = {
	route: 'login',
	title: 'Login'
};

export default function LoginView() {
	const hasError = new URLSearchParams(window.location.search).has('error');
	
  return (
    <LoginOverlay
      opened={true}
	  error={hasError}
      action="login" 
      title="Sistema de Terceros"
      description="Ingrese sus datos de acceso"
    />
  );
}