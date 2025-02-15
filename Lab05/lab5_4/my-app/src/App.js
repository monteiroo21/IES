import React, { useState, useEffect } from 'react';
import axios from "axios";

const client = axios.create({
  baseURL: "http://localhost:8080/api"
});

function App() {
  console.log("App component rendered");
  const [movies, setMovies] = useState([]);
  const [title, setTitle] = useState('');
  const [year, setYear] = useState('');
  const [searchTitle, setSearchTitle] = useState('');
  const [searchedMovie, setSearchedMovie] = useState('');
  const [quotes, setQuotes] = useState([]);
  const [quote, setQuote] = useState('');
  const [movie, setMovie] = useState('');
  const [searchQuote, setSearchQuote] = useState('');
  const [searchedQuote, setSearchedQuote] = useState('');
  const [latestQuotes, setLatestQuotes] = useState([]);

  useEffect(() => {
    const fetchMovie = async () => {
      try {
        const response = await client.get('/movies');
        setMovies(response.data);
      } catch (error) {
        console.error("Error fetching movies:", error);
      }
    };
  
    fetchMovie();
  }, []);

  const deleteMovie = async (id) => {
    await client.delete(`/movies/${id}`);
    setMovies(
      movies.filter((movie) => {
          return movie.id !== id;
      })
    );
  };

  const addMovie = async (title, year) => {
    let response = await client.post('/addmovie', {
      title: title,
      year: year,
    });
    setMovies((movies) => [response.data, ...movies]);
  };

  const handleSubmit = (e) => {
      e.preventDefault();
      addMovie(title, year);
  };

  const handleSearch = async (e) => {
    e.preventDefault();
    try {
      const response = await client.get(`/movies/title/${searchTitle}`);
      setSearchedMovie(response.data);
    } catch (error) {
      console.error("Error fetching the searched movie:", error);
      setSearchedMovie(null);
    }
  };

  useEffect(() => {
    const fetchQuote = async () => {
      try {
        const response = await client.get('/quotes');
        setQuotes(response.data);
      } catch (error) {
        console.error("Error fetching quotes:", error);
      }
    };
  
    fetchQuote();
  }, []);

  const deleteQuote = async (id) => {
    await client.delete(`/quotes/${id}`);
    setQuotes(
      quotes.filter((quote) => {
          return quote.id !== id;
      })
    );
  };

  const addQuote = async (quote, movieID) => {
    let response = await client.post('/addquote', {
      quote: quote,
      movie: {id: movieID},
    });
    setQuotes((quotes) => [response.data, ...quotes]);
  };

  const handleSubmitQuote = (e) => {
      e.preventDefault();
      addQuote(quote, movie);
  };

  const handleSearchQuote = async (e) => {
    e.preventDefault();
    try {
      const response = await client.get(`/quotes/quote/${searchQuote}`);
      setSearchedQuote(response.data);
    } catch (error) {
      console.error("Error fetching the searched quote:", error);
      setSearchedQuote(null);
    }
  };

  const randomQuote = async () => {
    try {
      const response = await client.get('/quote'); // Assuming your backend endpoint for a random quote is `/quote`
      const quote = response.data;
      console.log("Random Quote:", quote);
      alert(`Quote: "${quote.quote}"\nFrom Movie: "${quote.movie.title}"`);
    } catch (error) {
      console.error("Error fetching random quote:", error);
    }
  };

  useEffect(() => {
    const fetchLatestQuotes = async () => {
      try {
        const response = await client.get("/quotes/latest");
        setLatestQuotes(response.data);
      } catch (error) {
        console.error("Error fetching latest quotes:", error);
      }
    };

    fetchLatestQuotes();

    const interval = setInterval(fetchLatestQuotes, 10000);

    return () => clearInterval(interval); // Clean up interval on component unmount
  }, []);

  return (
    <div className='app'>
      <div className="search-movie-container">
        <form onSubmit={handleSearch}>
          <input 
            type="text" 
            className="form-control" 
            placeholder="Search by Title"
            value={searchTitle}
            onChange={(e) => setSearchTitle(e.target.value)}
          />
          <button type="submit">Search</button>
        </form>
        {searchedMovie && (
          <div className="movie-card">
            <h2 className="movie-title">{searchedMovie.title}</h2>
            <h3 className="movie-year">{searchedMovie.year}</h3>
          </div>
        )}
      </div>
      <div className="add-movie-container">
        <form onSubmit={handleSubmit}>
          <input type="text" className="form-control" value={title}
              onChange={(e) => setTitle(e.target.value)}
          />
          <input type="integer" className="form-control"
              value={year} onChange={(e) => setYear(e.target.value)} 
          ></input>
          <button type="submit">Add Movie</button>
        </form>
      </div>
      <div className="movies-container">
        {movies.length === 0 ? (
          <p>No movies found</p>
        ) : (
          movies.map((movie) => (
            <div className="movie-card" key={movie.id}>
              <h2 className="movie-title">{movie.title}</h2>
              <h3 className="movie-year">{movie.year}</h3>
              <div className="button">
                <div className="delete-btn" onClick={() => deleteMovie(movie.id)}>
                  Delete
                </div>
              </div>
            </div>
          ))
        )}
      </div>
      <div className="search-quote-container">
        <form onSubmit={handleSearchQuote}>
          <input 
            type="text" 
            className="form-control" 
            placeholder="Search by Quote"
            value={searchQuote}
            onChange={(e) => setSearchQuote(e.target.value)}
          />
          <button type="submit">Search</button>
        </form>
        {searchedQuote && (
          <div className="quote-card">
            <h2 className="quote-quote">{searchedQuote.quote}</h2>
            <h3 className="quote-movie">{searchedQuote.movie?.title}</h3>
          </div>
        )}
      </div>
      <div className="add-quote-container">
        <form onSubmit={handleSubmitQuote}>
          <input type="text" className="form-control" value={quote}
              onChange={(e) => setQuote(e.target.value)}
          />
          <input type="integer" className="form-control"
              value={movie} onChange={(e) => setMovie(e.target.value)} 
          ></input>
          <button type="submit">Add Quote</button>
        </form>
      </div>
      <div className="get-random-quote">
        <div className='random-btn' onClick={() => randomQuote()}>
          Random Quote
        </div>
      </div>
      <div className="quote-container">
        {quotes.length === 0 ? (
          <p>No quotes found</p>
        ) : (
          quotes.map((quote) => (
            <div className="quote-card" key={quote.id}>
              <h2 className="quote-quote">{quote.quote}</h2>
              <h3 className="quote-movie">{quote.movie?.title}</h3>
              <div className="button">
                <div className="delete-btn" onClick={() => deleteQuote(quote.id)}>
                  Delete
                </div>
              </div>
            </div>
          ))
        )}
      </div>
      <div className="latest-quotes-container">
        <h2>Latest Quotes</h2>
        <ul>
          {latestQuotes.map((quote, index) => (
            <li key={index}>
              <strong>Movie:</strong> {quote.movie.title} <br />
              <strong>Quote:</strong> {quote.quote}
            </li>
          ))}
        </ul>
      </div>
    </div>
  )
}

export default App;
