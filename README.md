# SE_betandruin2022
 Software Engineering project for the creation of a Bet &amp; Ruin interface program (2022).

 Editors: Iosu Abal, Nagore Bravo, Asier Septién, Ainhize Legarreta.
 Name of the team: 
 
 
 
 + a brief explanation of what you did for this sprint (including the main problems -technical or not - you faced  and how they were solved)
 
 + any other explanation worth mentioning (for example, any extra work done that you would like to hightlight or a justification of not-implemented work)
 
 
 
 
 
 
 
 
 
 
 
 
 + Set a Fee Use Case:
 To implement this use case we needed to create a new class in the Domain. This class is called Fee and has three different attributes.
 The first one is just an identifier, the second one is the result of a question (for example, the result for who is going to win question, could 
 be 'Real Sociedad'). The last attribute is the actual fee, how big is the benefit you get if your answer is correct.
 Now, every question has a list of Fees, since a question can have more than one answer and more than one fee.
 Speaking about the GUI, I created a new JFrame, which will be only available for the admin user. I had to check that there were events and questions for the selected date, and that a fee was not set.(For that I created an Exception). I had some issues when loading the calendar (since it was empty), and that was because
 the businessLogic was not properly instantiazed, so the GUI couldn't load it properly.
 
 
 
 + Create Event Use Case: 
Our task in this section is to add a new event, so that we can create a question or set a fee on that event later. It has been necessary to create a new GUI called CreateEventGUI, as well as several new methods in some of the classes: firstly, in the Business Logic, a “createEvent” method, and secondly, in the database, the general "createEvent" method. To ensure its correct operation, it has been essential to implement a method called "isAnyTeamPlaying", which checks if one of the teams is assigned to another event on the same date. Having selected a date, (not before the current one), when clicking on the button to create an Event, it is requested to write the name of each team. As mentioned above, should any of those teams not have another event on that date, the event will be successfully created and added to our database and thus the event combo box. Finally, the calendar will indicate that there is an event on that specific date, painting it.
 