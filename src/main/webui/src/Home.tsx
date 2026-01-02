import { useAuth } from 'oidc-react';
import { useState } from 'react';

function Home() {
  const { userData, signIn, signOut } = useAuth();
  const [data, setData] = useState<string | null>(null);
  const [error, setError] = useState<string | null>(null);

  const apiCall = (endpoint: string) => {
    fetch(endpoint, {
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${userData?.access_token}`
      }
    })
      .then(response => {
        if (!response.ok) {
          throw new Error(`HTTP error! ${response.status} status code received.`);
        }
        return response;
      })
      .then(response => response.text())
      .then(text => {
        setData(text);
        setError(null);
      })
      .catch(err => {
        setError(err.message);
        setData(null);
      });
  };

  const greets = () => apiCall('/quarkus-api/api/greets');
  const propagate = () => apiCall('/quarkus-api/api/propagate');

  return (
    <div className="bg-white dark:bg-gray-800 text-gray-900 dark:text-white px-6 py-8 min-h-screen flex flex-col items-center justify-center">
      <main className="rounded-lg p-8 bg-gray-100 dark:bg-gray-900 shadow-md ring shadow-xl ring-gray-900/5 ">
        <h1 className="text-center text-4xl font-bold underline">Hello {userData?.profile?.preferred_username ?? 'Guest'}!</h1>
        <p className="mt-4">This is a simple React application using Vite and Tailwind CSS.</p>
        {userData && <p className="mt-4 italic">{data}</p>}
        <div className="mt-8 flex gap-5 justify-between items-center">
          <div className="flex gap-5 items-center">
            <button onClick={greets} className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600">
              Greetings
            </button>
            <button onClick={propagate} className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600">
              With token propagation
            </button>
          </div>
          {userData ? (
            <button
              onClick={() => {
                signOut();
              }}
              className="px-4 py-2 bg-red-500 text-white rounded hover:bg-red-600"
            >
              Sign Out
            </button>
          ) : (
            <button onClick={() => signIn()} className="px-4 py-2 bg-green-500 text-white rounded hover:bg-green-600">
              Sign In
            </button>
          )}
        </div>
        {error && <p className="mt-4 text-red-500">{error}</p>}
      </main>
    </div>
  );
}

export default Home;
