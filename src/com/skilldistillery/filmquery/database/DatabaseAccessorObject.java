package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {
	private static final String url = "jdbc:mysql://localhost:3306/sdvid?useSSL=false";
	private static final String user = "student";
	private static final String pass = "student";

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private class QueryRunner implements AutoCloseable {
		private Connection conn;
		private PreparedStatement pstmt;
		private ResultSet rs;

		public ResultSet runQuery(String sql, String argsFormat, String... args) {
			try {
				if (argsFormat.length() != args.length) {
					System.err.println("Incorrect number of arguments");
					throw new NumberFormatException();
				}
				conn = DriverManager.getConnection(url, user, pass);
				pstmt = conn.prepareStatement(sql);
				for (int i = 0; i < args.length; i++) {
					try {
						switch (argsFormat.charAt(i)) {
							case 's':
							case 'S':
								pstmt.setString(i + 1, args[i]);
								break;
							case 'i':
							case 'I':
								pstmt.setInt(i + 1, Integer.parseInt(args[i]));
								break;
							default:
								throw new RuntimeException("Invalid runQuery Formating");
						}
					} catch (SQLException e) {
						System.err.println(e);
					}

				}

				rs = pstmt.executeQuery();
			} catch (SQLException e) {
				System.err.println(e);
			}
			return rs;
		}

		@Override
		public void close() throws SQLException {
			pstmt.close();
			conn.close();
		}

	}

	@Override
	public Film findFilmById(int filmId) {
		String sql = "SELECT film.id, film.title, film.description, film.release_year," +
						" film.language_id, film.rental_duration, " +
						"film.rental_rate, film.length, film.replacement_cost," +
						" film.rating, film.special_features, language.name " +
						"FROM film JOIN language ON language.id = film.language_id " +
						"WHERE film.id = ?";
		try ( // Dont Wrap
				QueryRunner qr = new QueryRunner(); // Dont Wrap
				ResultSet rs = qr.runQuery(sql, "i", String.valueOf(filmId));// Dont Wrap
		) {
			if (rs.next()) {
				return new Film(// Dont Wrap
						rs.getInt("film.id"), // Dont Wrap
						rs.getString("film.title"), // Dont Wrap
						rs.getString("film.description"), // Dont Wrap
						rs.getInt("film.release_year"), // Dont Wrap
						rs.getInt("film.language_id"), // Dont Wrap
						rs.getInt("film.rental_duration"), // Dont Wrap
						rs.getInt("film.rental_rate"), // Dont Wrap
						rs.getInt("film.length"), // Dont Wrap
						rs.getDouble("film.replacement_cost"), // Dont Wrap
						rs.getString("film.rating"), // Dont Wrap
						rs.getString("film.special_features"), // Dont Wrap
						rs.getString("language.name")// Dont Wrap
				);
			} else {
				return null;
			}
		} catch (SQLException e) {
			System.err.println(e);
		}
		return null;
	}

	@Override
	public Actor findActorById(int actorId) {
		String sql = "SELECT id, first_name, last_name" + "FROM actor " + "WHERE actor.id = ?";
		try ( // Dont Wrap
				QueryRunner qr = new QueryRunner(); // Dont Wrap
				ResultSet rs = qr.runQuery(sql, "i", String.valueOf(actorId));// Dont Wrap
		) {
			if (rs.next()) {
				return new Actor( // Dont Wrap
						rs.getInt("id"), // Dont Wrap
						rs.getString("first_name"), // Dont Wrap
						rs.getString("last_name")// Dont Wrap
				);
			} else {
				return null;
			}
		} catch (SQLException e) {
			System.err.println(e);
		}
		return null;
	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) {
		List<Actor> actorsInFilm = new ArrayList<>();
		String sql = "SELECT actor.id, actor.first_name, actor.last_name " +
						"FROM actor JOIN film_actor ON actor.id = film_actor.actor_id " +
						"WHERE film_actor.film_id = ?";
		try ( // Dont Wrap
				QueryRunner qr = new QueryRunner(); // Dont Wrap
				ResultSet rs = qr.runQuery(sql, "i", String.valueOf(filmId));// Dont Wrap
		) {

			while (rs.next()) {
				actorsInFilm.add(new Actor( // Dont Wrap
						rs.getInt("actor.id"), // Dont Wrap
						rs.getString("actor.first_name"), // Dont Wrap
						rs.getString("actor.last_name")// Dont Wrap
				));
			}
			return actorsInFilm;
		} catch (SQLException e) {
			System.err.println(e);
		}
		return null;
	}

	@Override
	public List<Film> findFilmByKeyWord(String keyword) {
		List<Film> possibleFilms = new ArrayList<>();
		String sql = "SELECT film.id, film.title, film.description, film.release_year," +
						" film.language_id, film.rental_duration, " +
						"film.rental_rate, film.length, film.replacement_cost," +
						" film.rating, film.special_features, language.name " +
						"FROM film JOIN language ON language.id = film.language_id " +
						"WHERE film.title LIKE ? OR film.description LIKE ?";
		String input = "%"+keyword+"%";
		try ( // Dont Wrap
				QueryRunner qr = new QueryRunner(); // Dont Wrap
				ResultSet rs = qr.runQuery(sql, "ss", input, input);// Dont Wrap
		) {

			while (rs.next()) {
				possibleFilms.add(new Film(// Dont Wrap
						rs.getInt("film.id"), // Dont Wrap
						rs.getString("film.title"), // Dont Wrap
						rs.getString("film.description"), // Dont Wrap
						rs.getInt("film.release_year"), // Dont Wrap
						rs.getInt("film.language_id"), // Dont Wrap
						rs.getInt("film.rental_duration"), // Dont Wrap
						rs.getInt("film.rental_rate"), // Dont Wrap
						rs.getInt("film.length"), // Dont Wrap
						rs.getDouble("film.replacement_cost"), // Dont Wrap
						rs.getString("film.rating"), // Dont Wrap
						rs.getString("film.special_features"), // Dont Wrap
						rs.getString("language.name")// Dont Wrap

				));
			}
			return possibleFilms;
		} catch (SQLException e) {
			System.err.println(e);
		}
		return null;
	}

}
