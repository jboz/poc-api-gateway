// import { AuthProvider } from 'oidc-react';
import { AuthProvider, AuthProviderProps } from 'oidc-react';
import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import Home from './Home';
import './index.css';

const oidcConfig: AuthProviderProps = {
  onSignIn: user => {
    // alert('You just signed in, congratz! Check out the console!');
    // console.log(user);
    // window.location.hash = '';
  },
  authority: 'http://192.168.1.42:8090/realms/secure-api',
  responseType: 'code',
  clientId: 'secure-api-client',
  clientSecret: 'EeNCvstsBLdJoBefaQuq7C8o0SBQwgTL',
  redirectUri: process.env.NODE_ENV === 'development' ? 'http://localhost:5173' : 'https://myapp.example.com/example-oidc-react'
};

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <AuthProvider {...oidcConfig}>
      <Home />
    </AuthProvider>
  </StrictMode>
);
