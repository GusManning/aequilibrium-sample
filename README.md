# aequilibrium-sample

## Table of Contents
* [Transformers](#Transformers)
* [Transformers API](#Transformers-API)
* [Fights](#Fights)
* [Fight Rules and Requirements](#Fight-Rules-and-Requirements)
* [Assumptions](#Assumptions)
* [Launch](#Launch)


## Overview

This project is a code test developed for Aequilibrium.  The application is a REST API developed in Java 11 using Spring Boot 2.4.2 and Maven.  The REST API simulates battles between Hasbro’s Transformers.  It allows for users to create, read, update, delete Transformers as well as pass a list of stored Transformers to a fight API which will return the results of a simulated battle.

### Transformers

Transformers are transforming robots disguised as planes, cars, or other vehicles (or even animals depending on the property).  Each Transformer has a list of eight Statistics representing the Strength, Intelligence, Speed, Endurance, Rank, Courage, Firepower and Skill of the Transformer.  All transformers have names and belong to one of two currently supported factions: The peace loving Autobots, and the evil Decepticons.  

#### Transformer JSON format

Example

```JSON
{
    "id": 1,
    "name": "Optimus Prime",
    "faction": "A",
    "stats": {
	"STRENGTH": 10,
	"SKILL": 9,
	"COURAGE": 10,
	"INTELLIGENCE": 10,
	"FIREPOWER": 10,
	"ENDURANCE": 10,
	"RANK": 10,
	"SPEED": 7
    }
}
```

* id: Long representing the index of a saved Transformer.
* Name: String, less that 100 characters
* Faction: “A” or “D” for Autobot or Decepticon respectively
* Stats: Stats are ranked 1 to 10, each associated one of the 8 Stat sections above.

### Transformers API

#### Get All: 
**Get:[Server-name]:8080/api/transformers**

Returns a list of Transformers in JSON format.

#### Get By Id: 
**Get:[Server-name]:8080/api/transformers/{id}**

Returns a single Transformer in JSON format, with an ID matching the ID passed in the URL.

#### Create Transformer: 
**Post:[Server-name]:8080/api/transformers**

Creates and saves a Transformer in the systems in-memory database.  To create a Transformer send a Create request with JSON Body of the Transformer you wish to create.  You must include all fields in this JSON payload EXCEPT for the id field.  Including id will cause your operation to fail.  Create can be used to make duplicates of already saved Transformers although their IDs will be different. 

Example:
```JSON
{
    "name": "Soundwave",
    "faction": "D",
    "stats": {
        "STRENGTH": 8,
        "SKILL": 5,
        "COURAGE": 9,
        "INTELLIGENCE": 6,
        "FIREPOWER": 6,
        "ENDURANCE": 9,
        "RANK": 8,
        "SPEED": 5
    }
}
```

#### Update Transformer:
**Put: [Server-name]:8080/api/transformers**

Updates an existing Transformer in the system.  The Body of this Put must be a JSON of a Transformer object, but it may be a partial Transformer JSON with only the id field being mandatory.  Fields not included will not be updated on the Transformer in the Database.

Example
```JSON
{
    "id": 5,
    "name": "Ironhide",
  
    "stats": {
    "STRENGTH": 9,
    "SKILL": 9,
    "COURAGE": 8,
    "RANK": 9,
    "SPEED": 8
    }
}
```
 

#### Delete Transformer:
Get:[Server-name]:8080/api/transformers/{id}

This will remove the Transformer from the database with the id matching the id provided in the URL.

### Fights 

Since peace was never an option between the Autobots and Decepticons, they wind up fighting frequently.  The Fight API simulates combat between these two forces.  When passed a list of Ids representing stored Transformers, the API divides them into Autobots and Decepticons and then returns the results of a mock battle as a JSON object.

#### Fight JSON format

The Fight interface returns a JSON object with the following format:

```JSON
{
    "battles": 3,
    "losers": [
         {
            "id": 6,
            "name": "Astrotrain",
            "faction": "D",
            "stats": {
                "INTELLIGENCE": 7,
                "FIREPOWER": 6,
                "COURAGE": 7,
                "ENDURANCE": 7,
                "RANK": 6,
                "SPEED": 10,
                "STRENGTH": 9,
                "SKILL": 8
            }
        }
    ],
    "victors": [
	{
            "id": 1,
            "name": "Optimus Prime",
            "faction": "A",
            "stats": {
                "INTELLIGENCE": 10,
                "FIREPOWER": 10,
                "COURAGE": 10,
                "ENDURANCE": 10,
                "RANK": 10,
                "SPEED": 7,
                "STRENGTH": 10,
                "SKILL": 9
            }
        },
        {
            "id": 5,
            "name": "Ironhide",
            "faction": "A",
            "stats": {
                "INTELLIGENCE": 10,
                "FIREPOWER": 10,
                "COURAGE": 8,
                "ENDURANCE": 10,
                "RANK": 9,
                "SPEED": 8,
                "STRENGTH": 9,
                "SKILL": 9
            }
        }
    ],
    "midroll": "https://www.youtube.com/watch?v=ODy_VrL_EXo",
    "victor": "Autobots"
}
```

* Battles: Number of fought between two Transformers
* Losers: A List of Transformers in JSON who survived the fight on the losing side.
* Victors: A List of Transformers in JSON who survived the fight on the winners side.
* Midroll: A link to a short video clip if relevant
* Victor: String name of the winning faction.

#### Simulate Fight:
**POST:[Server-name]:8080/api/fight**

Posting a JSON List of Transformer Ids will return a Fight between all Posted Transformers as Combatants.  Any IDs that do not correspond to saved Transformers will be ignored.

Example List:

This will return a fight between the six default Transformers loaded on startup. 
```JSON
[
    1,
    2,
    3,
    4,
    5,
    6
]
```
  
### Fight Rules and Requirements

* Transformers are split into two teams based on if they are Autobots or Decepticons.
* Teams face off against one another according to their Rank Stat, from highest to lowest.  Transformers without a counterpart to be matched up against are skipped.
* Fights are resolved via the following rules in order, losers are destroyed and winners survie.
	* Any Transformer named “Optimus Prime” or “Predaking” win their fight automatically.  If two Transformers with one or both of those names fight each other the fight ends immediately and all fighters are destroyed.
	* Any Transformer with 4 less courage and 3 less Strength then their opponent runs away from the fight.  This grants the enemy one win.
	* Transformers with 3 or more points of Skill then their opponent win.
	* Transformers have the sum of their Strength, Intelligence, Speed, Endurance, and Firepower compared.  The one with the largest sum wins.  In the event of a tie both fighters are destroyed.

#### Assumptions

* Transformers who flee survive as if they were skipped.
* Transformers who lose their fight are destroyed, the requirements don’t explicitly say this, it's just inferred from the example.
* Since victory is awarded on most eliminations, fight that are entirely one sided (ie. all Autobots and all Decepticons) will result in a tie.  
* If the event of an apocalyptic Optimus-Predaking matchup the force with the most Transformers on their side will lose since the enemy will have destroyed the most Transformers.  
* Since apocalyptic fights stop immediately this limits the number of battles.
* Fighters are paired up from highest rank to lowest.

## Launch

To get started with this code first you will need to download and unpack the file structure.  
You will need Maven on your computer, along with Java 11 installed.  You can run the application from the command line or a BASH console by navigating to the project’s Pom.xml directory and running the following command:

`
mvn spring-boot:run
`

If you get a message saying that mvn was not found make sure that Maven is in your PATH settings.

Provided you make no changes to your application.properties the [Server-name] mentioned above will be ‘localhost’ by default and you should be able to test if things worked by going to ‘localhost:8080/api/transformers’ in a web browser once the application has finished loading.

Tests can be run using the following command from the directory with the project’s Pom.xml file:

`
mvn test
`
