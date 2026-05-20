Vladislav Lamakin
CSI207 - OOP
Final Project: D&D Battle Simulator

to run the program:
1.	open project in eclipse
2.	make sure 'resources/battle_setup.txt' is in the root dir
3.	run 'Main.java' as a java application
4.	a swing gui window will appear, create your character and press the
	'into the fray!' button to begin
	
this project uses two different input formats, file and swing text fields.
the swing text fields are self explanatory, but for the file it uses comma-separated
variables, something like this:

format:
	Class,Name,HP,AC,Stat
	
so some example inputs would be:
	Warrior,Aragorn,120,18,8
	Mage,Gandalf,80,12,10
	Rogue,Bilbo,90,14,9
	Monster,Goblin,50,8,5
	Monster,Orc,70,13,8
	Monster,Troll,60,10,6
	
if 'battle_setup.txt' is missing or misplaced, a popup will display that the file
could not be found
	
the stat will change depending on a characters class. there is a default
party provided by the file 'battle_setup.txt', as well as a party member
created by the user using the gui.

assumptions:
	mages start with 2 fireballs and 3 missiles, hard coded
	initiative is rolled at the start with a d20
	rogue's sneak attack is only available on targets <20% hp or 100% hp
	fireball, magic missile, and sneak attack are guaranteed hits
	program exits upon closing gui window or once user confirms victory/defeat
	
i spent roughly 30 hours trying to understand swing and i am moderately proud
with the outcome of this project. the gui system uses a callback to update the
gui in real time while the logic runs in the background.
this project uses abstract classes Character and Combatant, as well as a BattleCallback 
interface to decouple the gui from the battle logic.
all combatants are stored in an arraylist and java automatically calls the
correct instance of takeTurn, simultaneously using turn-based logic and using
polymorphism.
all classes have a clear purpose.
