# Question 1 - Object Design (10 pts)  

4/10  
Comment: Delegation should be used. There should be at least one method in each class  

# Question 2 - UML Diagram (20 pts)  

16/20   
Comment:  
Solution:  
[1] Flight (number, origin, destination)  
[1] Traveler (name or id)  
[1] BookingOwner (name or id)  
[1] Booking (id or confirmation number, date)  
[3 pts] Booking ------ Flight  
* 1..*  
[3 pts] Booking ------ Traveler  
1 1..*  
[3 pts] Booking ------ BookingOwner  
1..* 1  
[3 pts] BookingOwner ----- Traveler  
1 1  
(Or: BookingOwner as a subclass of Traveler)  
[4 pts] Ticket: Association class between Booking and Traveler  

# Question 3 - Design Patterns (12 pts)  

12/12  

# Questoin 4 - Streams (14 pts)  

4.1 - 9/9  
4.2 - 5/5  

# Question 5 - Streams (21 pts)  

5.1 - 5.5/7  
Comment: Missing List<String> return (0.5 pts), .map to coder name (1 pt)  
5.2 - 3.5/7  
Comment: Missing Map<Language,Set> return (1 pt), groupingBy (0.5 pts), Coder::getLanguage (1 pt), toSet() (1 pt)  
5.3 - 7/7  

# Question 6 - Multithreading (20 pts)  
6.1 - 8/8  
6.2 - 4/4  
6.2 - 2/8  
Comment: +6 Scenario of potential problem unapplied rubric item + 2 pts Descripton of problem fix  
  
# Question 7 - Multithreading (15 pts)  
8/15  
Comment: Missing T1 and T2 BLOCKED, T1 RUNNABLE, T1 back to RUNNABLE, T2 RUNNABLE  

# Question 8 - Inheritance (13 pts)  
10.5/13  
Comment: Missing + 0.5 pts center in Circle class, + 3 pts override of area method using ALL of Circle area, plus more
(show the logic that proves the usage of super) in Cylinder class
