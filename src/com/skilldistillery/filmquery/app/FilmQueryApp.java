package com.skilldistillery.filmquery.app;

import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {
   
  DatabaseAccessor db = new DatabaseAccessorObject();

  public static void main(String[] args) {
    FilmQueryApp app = new FilmQueryApp();
    //app.test();
    app.launch();
  }

  private void test() {
    Film film = db.findFilmById(1);
    System.out.println(film);
  }

  private void launch() {
    Scanner input = new Scanner(System.in);
    
    startUserInterface(input);
    
    input.close();
  }

  private void startUserInterface(Scanner input) {
	  while(true) {
		  printMenu();
		  int userChoice = 0;
		  String userString = input.nextLine();
		  try {
			  userChoice = Integer.parseInt(userString);
		  }catch(NumberFormatException e){
			  System.out.println("Please enter a number.");
			  System.out.println();
			  continue;
		  }
		  if(userChoice == 3) {
			  break;
		  }
		  if(userChoice == 1) {
			  int userIDChoice = 0;
			  while (true) {
				  System.out.print("Enter an id:");
				  userString = input.nextLine();
				  try {
					  userIDChoice = Integer.parseInt(userString);
					  break;
				  }catch(NumberFormatException e){
					  System.out.println("Please enter a number.");
					  System.out.println();
					  continue;
				  }
			  }
			  Film film = db.findFilmById(userIDChoice);
			  if(film == null) {
				  System.out.println("The film by that id does not exist.");
			  }else {
				  film.setActors(db.findActorsByFilmId(userIDChoice));

				  System.out.println("Film: " + film.getTitle());
				  System.out.println("\tYear: " + film.getReleaseYear());
				  System.out.println("\tRating: " + film.getRating());
				  System.out.println("\tDescription: " + film.getDescription());
				  System.out.println("\tLanguage: " + film.getLanguage());
				  System.out.println("\tCast:");
				  for(Actor actor : film.getActors()) {
					  System.out.println("\t\t" + actor.getFirstName() + " " + actor.getLastName());
				  }
				  
			  }
			  System.out.println();
		  }
		  
		  if(userChoice == 2) {
			  int userIDChoice = 0;
			  System.out.print("Enter a keyword:");
			  userString = input.nextLine();
			  
			  List<Film> films = db.findFilmByKeyWord(userString);
			  if(films == null || films.size() == 0) {
				  System.out.println("No films contain that keyword.");
			  }else {
				  for(Film film : films) {
					  System.out.println(film);
				  }
			  }
			  System.out.println();
		  }
	  }
  }
  private void printMenu() {
	  System.out.println("1) Look up a film by its id.");
	  System.out.println("2) Look up a film by a search keyword.");
	  System.out.println("3) Exit the application.");
  }

}
