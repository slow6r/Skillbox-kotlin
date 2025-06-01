package warrior

import warrior.AbstractWarrior
import weapon.Weapons

class Soldier : AbstractWarrior(
    maxHealth = 100,
    dodgeChance = 20,
    accuracy = 60,
    weapon = Weapons.createPistol()
)