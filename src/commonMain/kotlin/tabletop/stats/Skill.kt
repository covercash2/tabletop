/* (C)2023 */
package tabletop.stats

sealed interface Skill : Proficiency {
    val ability: Ability
}

data object Acrobatics : Skill {
    override val ability: Ability
        get() = Dexterity
}
data object AnimalHandling : Skill {
    override val ability: Ability
        get() = Wisdom
}

data object Arcana : Skill {
    override val ability: Ability
        get() = Intelligence
}

data object Athletics : Skill {
    override val ability: Ability
        get() = Strength
}

data object Deception : Skill {
    override val ability: Ability
        get() = Charisma
}

data object History : Skill {
    override val ability: Ability
        get() = Intelligence
}

data object Insight : Skill {
    override val ability: Ability
        get() = Wisdom
}

data object Intimidation : Skill {
    override val ability: Ability
        get() = Charisma
}

data object Investigation : Skill {
    override val ability: Ability
        get() = Intelligence
}

data object Medicine : Skill {
    override val ability: Ability
        get() = Wisdom
}

data object Nature : Skill {
    override val ability: Ability
        get() = Intelligence
}

data object Perception : Skill {
    override val ability: Ability
        get() = Wisdom
}

data object Performance : Skill {
    override val ability: Ability
        get() = Charisma
}

data object Persuasion : Skill {
    override val ability: Ability
        get() = Charisma
}

data object Religion : Skill {
    override val ability: Ability
        get() = Intelligence
}

data object SleightOfHand : Skill {
    override val ability: Ability
        get() = Dexterity
}

data object Stealth : Skill {
    override val ability: Ability
        get() = Dexterity
}

data object Survival : Skill {
    override val ability: Ability
        get() = Wisdom
}
