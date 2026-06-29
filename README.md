# D&D Battle Simulator

A **Java Swing application** that simulates turn-based combat in the style of Dungeons & Dragons.  
Players can **create a character**, **choose a class**, and **battle a pre-loaded party of monsters** through an interactive GUI with real-time combat logging.

---

## Features

- Abstract class hierarchy with `Character` and `Combatant` base classes
- Polymorphic `takeTurn()` dispatch for all combatant types
- Three playable classes: Warrior, Mage, and Rogue — each with unique abilities
- Monster AI that auto-targets random living characters
- Initiative-based turn order rolled with a d20 at battle start
- Dual input formats: Swing GUI fields and a CSV config file (`battle_setup.txt`)
- Real-time battle log updated via a `BattleCallback` interface
- Battle logic runs in the background, fully decoupled from the GUI

---

## Examples

### Character Creation Panel

Players fill in their character's name and stats through the GUI, then click **"Into the Fray!"** to begin.

<img width="598" height="398" alt="image" src="https://github.com/user-attachments/assets/80fa3aad-2ae3-46e4-98cf-1dcd84fa223c" />


### Initiative Roll

At the start of combat, every combatant rolls a d20 for turn order:

<img width="598" height="398" alt="image" src="https://github.com/user-attachments/assets/cd9ab8d6-2905-4717-abc6-8e85aa5b9e4a" />


### Warrior's Turn

```
== Aragorn's turn! ==
Choose a target:
  1) Goblin (50/50 HP)
  2) Orc (70/70 HP)
  3) Troll (60/60 HP)
> 2
Aragorn strikes Orc for 14 damage!
```

### Mage's Turn

```
== Gandalf's turn! ==
Choose a target:
  1) Goblin (50/50 HP)
  2) Orc (56/70 HP)
  3) Troll (60/60 HP)
> 1
Choose an action:
  1) Fireball 2/2
  2) Magic Missile 3/3
  3) Basic Attack
> 1
Gandalf deals 27 damage to Goblin!
Goblin has been slain!
```

### Rogue's Turn

```
== Bilbo's turn! ==
Choose a target:
  1) Orc (56/70 HP)
  2) Troll (60/60 HP)
> 2
Choose an action:
  1) Sneak Attack
  2) Basic Attack
> 1
Bilbo slashes Troll for 19 damage!
```

### Monster's Turn

```
== Orc's turn! ==
Orc bashed Aragorn for 11 damage!
```

### Battle End

<img width="263" height="173" alt="image" src="https://github.com/user-attachments/assets/844746d8-4f72-4285-a607-1f41459f89b4" />

---

## Technical Implementation

### Class Hierarchy

- **`Combatant`** (abstract): Base class with name, HP, AC, initiative, and abstract `takeTurn()`
- **`Character`** (abstract, extends `Combatant`): Base for all player-controlled classes with abstract `getAttackModifier()`
- **`Warrior`**, **`Mage`**, **`Rogue`** (extend `Character`): Each implements unique turn logic
- **`Monster`** (extends `Combatant`): Auto-attacks a random living character

### Key Design Patterns

- **Polymorphism**: `Battle.run()` iterates a single `ArrayList<Combatant>` and calls `takeTurn()` on each — the correct subclass method is invoked automatically
- **Callback Interface**: `BattleCallback` decouples the `Battle` logic from the Swing GUI, allowing the log and HP panels to update in real time
- **Generics & Wildcards**: `getLiving(ArrayList<T>)` uses a bounded type parameter; `allDead()` uses a wildcard (`? extends Combatant`) for flexible list checking

### Combat Rules

| Class   | Stat | Attack          | Special Ability                                      |
|---------|------|-----------------|------------------------------------------------------|
| Warrior | STR  | d8 + STR        | High base damage                                     |
| Mage    | INT  | d6 + INT        | Fireball (2d10, 2 uses) · Magic Missile (3d4, 3 uses)|
| Rogue   | DEX  | d6 + DEX        | Sneak Attack (2d6) on full HP or ≤20% HP targets     |
| Monster | STR  | d6 + STR        | Auto-targets random living character                 |

---

## Running the Program

1. Open the project in **Eclipse**
2. Ensure `resources/battle_setup.txt` is present in the project root
3. Run `Main.java` as a Java application
4. The Swing GUI will launch — create your character and press **"Into the Fray!"**

> If `battle_setup.txt` is missing or misplaced, a popup will notify you that the file could not be found.

---

## Battle Setup File

The default party and monsters are loaded from `resources/battle_setup.txt` using a CSV format:

```
Format:
  Class,Name,HP,AC,Stat

Example entries:
  Warrior,Aragorn,120,18,8
  Mage,Gandalf,80,12,10
  Rogue,Bilbo,90,14,9
  Monster,Goblin,50,8,5
  Monster,Orc,70,13,8
  Monster,Troll,60,10,6
```

The `Stat` field maps to the class's primary attribute (STR for Warrior/Monster, INT for Mage, DEX for Rogue). The player's own character is created through the GUI and added on top of the file-loaded party.

---

## Assumptions

- Mages start every battle with exactly 2 Fireballs and 3 Magic Missiles
- Initiative is rolled fresh each battle with a d20
- Fireball, Magic Missile, and Sneak Attack are guaranteed hits
- Rogue's Sneak Attack is only available against targets at 100% HP or ≤20% HP
- The program exits when the GUI window is closed or after the player confirms the victory/defeat dialog

---

### Author

Vladislav Lamakin  
<lamakinvladislav@gmail.com>
