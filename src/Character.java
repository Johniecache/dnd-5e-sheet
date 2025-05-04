import java.util.Arrays;

/**
 * Handles all text field values except for inventory
 */
public class Character {
    /**
     * Initialize settings
     */
    private Settings settings;
    /**
     * header components
     */
    private String name, player, _class, race, background, deity, level, align;
    /**
     * Array for header components
     */
    private String[] headers;
    /**
     * Stat components
     */
    private int ac, hp, in, maxhp, hpdie, temphp, movespeed, passperception;
    /**
     * Array for stat components
     */
    private int[] stats;
    /**
     * Score components
     */
    private int strength, dexterity, constitution, intelligence, wisdom, charisma;
    /**
     * Array for score components
     */
    private int[] scores;
    /**
     * Proficiency components
     */
    private String prof_s1, prof_s2, prof_s3, prof_s4, prof_s5, prof_s6, prof_s7, prof_s8, prof_s9, prof_s10, prof_s11, prof_s12;
    /**
     * Array of proficiency slots
     */
    private String[] prof_slots;
    /**
     * Skill components
     */
    private int athletics, acrobatics, sleightofhand, stealth, arcana, history, investigation, nature, religion, animalhanding,
                insight, medicine, perception, survival, deception, intimidation, performance, persuasion;
    /**
     * Array for skill components
     */
    private int[] skills;
    /**
     * Modifier and Save components
     */
    private int str_mod, str_save, dex_mod, dex_save, con_mod, con_save, int_mod, int_save, wis_mod, wis_save, char_mod, char_save;
    /**
     * Array for modifier and save components
     */
    private int[] mods, saves;
    /**
     * Equipped Equipment components
     */
    private String ee_name_s1, ee_name_s2, ee_name_s3, ee_name_s4, ee_name_s5, ee_name_s6, ee_name_s7, ee_attack_s1, ee_attack_s2,
            ee_attack_s3, ee_attack_s4, ee_attack_s5, ee_attack_s6, ee_attack_s7, ee_damage_s1, ee_damage_s2, ee_damage_s3,
            ee_damage_s4, ee_damage_s5, ee_damage_s6, ee_damage_s7;
    /**
     * Array for equipped equipment components
     */
    private String[] equipped_equipment;
    /**
     * Currency components
     */
    private int cp, sp, gp, pp;
    /**
     * Array for currency components
     */
    private int[] currencies;
    /**
     * Misc components
     */
    private String passives;
    /**
     * Array for misc components
     */
    private String[] misc;

    /**
     * Default constructor for character class
     * @param name Name
     */
    public Character(String name){
        this.name = name; // set instance as passed value
        this.player = ""; // instantiate as empty string
        this._class = ""; // instantiate as empty string
        this.race = ""; // instantiate as empty string
        this.background = ""; // instantiate as empty string
        this.deity = ""; // instantiate as empty string
        this.level = String.valueOf(1); // instantiate as 1
        this.align = ""; // instantiate as empty string

        headers = new String[]{this.name,this.player,this._class,this.race,this.background,this.deity,this.level,this.align}; // instantiate headers to instance variables
        stats = new int[]{0,0,0,0,0,0,0,0}; // instantiate stats default as 0
        scores = new int[]{0,0,0,0,0,0}; // instantiate scores default as 0
        prof_slots = new String[12]; // new prof slot array wtih 12 slots
        Arrays.fill(prof_slots, ""); // fill prof slots as empty string
        skills = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}; // instantiates skills default as 0
        mods = new int[]{0,0,0,0,0,0}; // instantiates mods default as 0
        saves = new int[]{0,0,0,0,0,0}; // instantiates saves default as 0
        equipped_equipment = new String[21]; // instantiates equipped equipment as new  string array
        Arrays.fill(equipped_equipment,""); // fill equipped equipment array with empty string
        currencies = new int[]{0,0,0,0}; // instantiates currencies default as 0
        misc = new String[1]; // create misc array
        Arrays.fill(misc,""); // fill mist with empty string
    }

    // Headers

    /**
     * Gets the headers as an array
     * @return headers
     */
    public String[] getHeaders(){return this.headers;}

    /**
     * Gets the headers from passed index
     * @param index index
     * @return header component at index
     */
    public String getHeaders(int index){return this.headers[index];}

    /**
     * Gets the name of character
     * @return name
     */
    public String getName(){return this.name;}

    /**
     * Gets the class of the character
     * @return class
     */
    public String get_Class(){return this._class;}

    // Stats

    /**
     * Gets the stats as an array
     * @return stats
     */
    public int[] getStats(){return this.stats;}

    /**
     * Gets stats at a passed index
     * @param index index
     * @return stat component at index
     */
    public int getStats(int index){return this.stats[index];}

    // Scores

    /**
     * Gets the scores as an array
     * @return scores
     */
    public int[] getScores(){return this.scores;}

    /**
     * Gets score component at passed index
     * @param index index
     * @return score at passed index
     */
    public int getScores(int index){return this.scores[index];}

    // Prof Slots

    /**
     * Gets proficiencies as an array
     * @return prof slots
     */
    public String[] getProfSlots(){return this.prof_slots;}

    /**
     * Gets proficiency component at passed index
     * @param index index
     * @return prof slot at index
     */
    public String getProfSlots(int index){return this.prof_slots[index];}

    // Skills

    /**
     * Gets skills as an array
     * @return skills
     */
    public int[] getSkills(){return this.skills;}

    /**
     * Gets skills at a passed index
     * @param index index
     * @return skill
     */
    public int getSkills(int index){return this.skills[index];}

    // Mods

    /**
     * Gets mods as an array
     * @return mods
     */
    public int[] getMods(){return this.mods;}

    /**
     * Gets modifier at a passed index
     * @param index index
     * @return modifier
     */
    public int getMods(int index){return this.mods[index];}

    // Saves

    /**
     * Gets saves as an array
     * @return saves
     */
    public int[] getSaves(){return this.saves;}

    /**
     * Gets save at a passed index
     * @param index index
     * @return save
     */
    public int getSaves(int index){return this.saves[index];}

    // Equipped Equipment

    /**
     * Gets array of equipped equipment
     * @return equipped equipment
     */
    public String[] getEquippedEquipment(){return this.equipped_equipment;}

    /**
     * Gets equipped equipment at a passed index
     * @param index index
     * @return equipped equipment
     */
    public String getEquippedEquipment(int index){return this.equipped_equipment[index];}

    // Currencies

    /**
     * Get currencies as an array
     * @return currencies
     */
    public int[] getCurrencies(){return this.currencies;}

    /**
     * Get currency at passed index
     * @param index index
     * @return currency
     */
    public int getCurrencies(int index){return this.currencies[index];}

    // Information

    /**
     * Misc as an array
     * @return misc
     */
    public String[] getMisc(){return this.misc;}

    /**
     * Gets misc at a passed index
     * @param index index
     * @return misc
     */
    public String getMisc(int index){return this.misc[index];}

    //--------------------
    // Setters
    //--------------------

    // Headers

    /**
     * Sets headers with passed array
     * @param values values
     */
    public void setHeaders(String[] values) {
        this.headers = values; // instance array to passed array
        this.name = values[0]; // set name
        this.player = values[1]; // set player
        this._class = values[2]; // set class
        this.race = values[3]; // set race
        this.background = values[4]; // set background
        this.deity = values[5]; // set deity
        this.level = values[6]; // set level
        this.align = values[7]; // set align
    }

    /**
     * Sets header component at passed index
     * @param index position
     * @param value value
     */
    public void setHeaders(int index, String value) {
        if (index >= 0 && index < headers.length) { // make sure passed value is valid
            headers[index] = value; // set value as header at index

            switch (index) { // switch at index
                case 0 -> name = value; // set name
                case 1 -> player = value; // set player
                case 2 -> _class = value; // set class
                case 3 -> race = value; // set race
                case 4 -> background = value; // set background
                case 5 -> deity = value; // set deity
                case 6 -> level = value; // set level
                case 7 -> align = value; // set alignment
            }
        }
    }

    /**
     * Sets name of the character
     * @param name name
     */
    public void setName(String name){this.name = name;}

    /**
     * Sets class of the character
     * @param _class class
     */
    public void set_Class(String _class){this._class = _class;}

    // Stats

    /**
     * Sets stats as passed array
     * @param stats stats
     */
    public void setStats(int[] stats) {
        this.stats = stats;
        this.ac = stats[0];
        this.hp = stats[1];
        this.in = stats[2];
        this.maxhp = stats[3];
        this.hpdie = stats[4];
        this.temphp = stats[5];
        this.movespeed = stats[6];
        this.passperception = stats[7];
    }

    /**
     * Sets stats at index position as passed string
     * @param index position
     * @param stat value
     */
    public void setStats(int index, String stat) {
        try {
            int value = Integer.parseInt(stat);
            if (index >= 0 && index < stats.length) {
                stats[index] = value;
                switch (index) {
                    case 0 -> ac = value;
                    case 1 -> hp = value;
                    case 2 -> in = value;
                    case 3 -> maxhp = value;
                    case 4 -> hpdie = value;
                    case 5 -> temphp = value;
                    case 6 -> movespeed = value;
                    case 7 -> passperception = value;
                }
            }
        } catch (NumberFormatException ignore) {}
    }

    // Scores

    /**
     * Sets scores as passed array
     * @param scores scores
     */
    public void setScores(int[] scores) {
        this.scores = scores;
        this.strength = scores[0];
        this.dexterity = scores[1];
        this.constitution = scores[2];
        this.intelligence = scores[3];
        this.wisdom = scores[4];
        this.charisma = scores[5];
    }

    /**
     * Sets score at index as passed value
     * @param index position
     * @param score score
     */
    public void setScores(int index, String score) {
        try {
            int value = Integer.parseInt(score);
            if (index >= 0 && index < scores.length) {
                scores[index] = value;
                switch (index) {
                    case 0 -> {
                        strength = value;
                        setMods(0,"strength");
                    }
                    case 1 -> dexterity = value;
                    case 2 -> constitution = value;
                    case 3 -> intelligence = value;
                    case 4 -> wisdom = value;
                    case 5 -> charisma = value;
                }
            }
        } catch (NumberFormatException ignore) {}
    }

    // Prof Slots

    /**
     * Sets Proficiency slots as passed array
     * @param values proficiencies
     */
    public void setProfSlots(String[] values) {
        this.prof_slots = values;

        this.prof_s1 = values[0];
        this.prof_s2 = values[1];
        this.prof_s3 = values[2];
        this.prof_s4 = values[3];
        this.prof_s5 = values[4];
        this.prof_s6 = values[5];
        this.prof_s7 = values[6];
        this.prof_s8 = values[7];
        this.prof_s9 = values[8];
        this.prof_s10 = values[9];
        this.prof_s11 = values[10];
        this.prof_s12 = values[11];
    }

    /**
     * Sets proficiency at index as passed value
     * @param index position
     * @param value value
     */
    public void setProfSlots(int index, String value) {
        if (index >= 0 && index < prof_slots.length) {
            prof_slots[index] = value;

            switch (index) {
                case 0 -> prof_s1 = value;
                case 1 -> prof_s2 = value;
                case 2 -> prof_s3 = value;
                case 3 -> prof_s4 = value;
                case 4 -> prof_s5 = value;
                case 5 -> prof_s6 = value;
                case 6 -> prof_s7 = value;
                case 7 -> prof_s8 = value;
                case 8 -> prof_s9 = value;
                case 9 -> prof_s10 = value;
                case 10 -> prof_s11 = value;
                case 11 -> prof_s12 = value;
            }
        }
    }

    // Skills

    /**
     * Sets skills as passed array
     * @param skills skills
     */
    public void setSkills(int[] skills) {
        this.skills = skills;
        this.athletics = skills[0];
        this.acrobatics = skills[1];
        this.sleightofhand = skills[2];
        this.stealth = skills[3];
        this.arcana = skills[4];
        this.history = skills[5];
        this.investigation = skills[6];
        this.nature = skills[7];
        this.religion = skills[8];
        this.animalhanding = skills[9];
        this.insight = skills[10];
        this.medicine = skills[11];
        this.perception = skills[12];
        this.survival = skills[13];
        this.deception = skills[14];
        this.intimidation = skills[15];
        this.performance = skills[16];
        this.persuasion = skills[17];
    }

    /**
     * Sets skill at index as passed value
     * @param index position
     * @param skill value
     */
    public void setSkills(int index, String skill) {
        try {
            int value = Integer.parseInt(skill);
            if (index >= 0 && index < skills.length) {
                skills[index] = value;
                switch (index) {
                    case 0 -> athletics = value;
                    case 1 -> acrobatics = value;
                    case 2 -> sleightofhand = value;
                    case 3 -> stealth = value;
                    case 4 -> arcana = value;
                    case 5 -> history = value;
                    case 6 -> investigation = value;
                    case 7 -> nature = value;
                    case 8 -> religion = value;
                    case 9 -> animalhanding = value;
                    case 10 -> insight = value;
                    case 11 -> medicine = value;
                    case 12 -> perception = value;
                    case 13 -> survival = value;
                    case 14 -> deception = value;
                    case 15 -> intimidation = value;
                    case 16 -> performance = value;
                    case 17 -> persuasion = value;
                }
            }
        } catch (NumberFormatException ignore) {}
    }

    // Mods

    /**
     * Sets mods as passed array
     * @param mods mods
     */
    public void setMods(int[] mods) {
        this.mods = mods;
        this.str_mod = mods[0];
        this.dex_mod = mods[1];
        this.con_mod = mods[2];
        this.int_mod = mods[3];
        this.wis_mod = mods[4];
        this.char_mod = mods[5];
    }

    /**
     * Sets mod component as passed value at index
     * @param index position
     * @param mod value
     */
    public void setMods(int index, String mod) {
        try {
            int value = Integer.parseInt(mod);
            if (index >= 0 && index < mods.length) {
                mods[index] = value;
                switch (index) {
                    case 0 -> str_mod = value;
                    case 1 -> dex_mod = value;
                    case 2 -> con_mod = value;
                    case 3 -> int_mod = value;
                    case 4 -> wis_mod = value;
                    case 5 -> char_mod = value;
                }
            }
        } catch (NumberFormatException ignore) {}
    }

    // Saves

    /**
     * Sets saves as passed array
     * @param save save
     */
    public void setSaves(int[] save) {
        this.saves = save;
        this.str_save = saves[0];
        this.dex_save = saves[1];
        this.con_save = saves[2];
        this.int_save = saves[3];
        this.wis_save = saves[4];
        this.char_save = saves[5];
    }

    /**
     * Sets save at index as passed value
     * @param index position
     * @param save value
     */
    public void setSaves(int index, String save) {
        try {
            int value = Integer.parseInt(save);
            if (index >= 0 && index < saves.length) {
                saves[index] = value;
                switch (index) {
                    case 0 -> str_save = value;
                    case 1 -> dex_save = value;
                    case 2 -> con_save = value;
                    case 3 -> int_save = value;
                    case 4 -> wis_save = value;
                    case 5 -> char_save = value;
                }
            }
        } catch (NumberFormatException ignore) {}
    }

    // Equipped Equipment

    /**
     * Sets equipped equipment as passed array
     * @param equipment equipment
     */
    public void setEquippedEquipment(String[] equipment){
        this.equipped_equipment = equipment; // assign passed equipment array to instance variable

        this.ee_name_s1 = equipment[0]; // set name slot 1
        this.ee_name_s2 = equipment[1]; // set name slot 2
        this.ee_name_s3 = equipment[2]; // set name slot 3
        this.ee_name_s4 = equipment[3]; // set name slot 4
        this.ee_name_s5 = equipment[4]; // set name slot 5
        this.ee_name_s6 = equipment[5]; // set name slot 6
        this.ee_name_s7 = equipment[6]; // set name slot 7

        this.ee_attack_s1 = equipment[7]; // set attack slot 1
        this.ee_attack_s2 = equipment[8]; // set attack slot 2
        this.ee_attack_s3 = equipment[9]; // set attack slot 3
        this.ee_attack_s4 = equipment[10]; // set attack slot 4
        this.ee_attack_s5 = equipment[11]; // set attack slot 5
        this.ee_attack_s6 = equipment[12]; // set attack slot 6
        this.ee_attack_s7 = equipment[13]; // set attack slot 7

        this.ee_damage_s1 = equipment[14]; // set damage slot 1
        this.ee_damage_s2 = equipment[15]; // set damage slot 2
        this.ee_damage_s3 = equipment[16]; // set damage slot 3
        this.ee_damage_s4 = equipment[17]; // set damage slot 4
        this.ee_damage_s5 = equipment[18]; // set damage slot 5
        this.ee_damage_s6 = equipment[19]; // set damage slot 6
        this.ee_damage_s7 = equipment[20]; // set damage slot 7
    }

    /**
     * Sets equipped equipment as passed value at position
     * @param index position
     * @param equipment value
     */
    public void setEquippedEquipment(int index, String equipment){
        if(index >= 0 && index < this.equipped_equipment.length){
            this.equipped_equipment[index] = equipment; // set value in array
            switch(index){
                case 0 -> ee_name_s1 = equipment; // update name slot 1
                case 1 -> ee_name_s2 = equipment; // update name slot 2
                case 2 -> ee_name_s3 = equipment; // update name slot 3
                case 3 -> ee_name_s4 = equipment; // update name slot 4
                case 4 -> ee_name_s5 = equipment; // update name slot 5
                case 5 -> ee_name_s6 = equipment; // update name slot 6
                case 6 -> ee_name_s7 = equipment; // update name slot 7
                case 7 -> ee_attack_s1 = equipment; // update attack slot 1
                case 8 -> ee_attack_s2 = equipment; // update attack slot 2
                case 9 -> ee_attack_s3 = equipment; // update attack slot 3
                case 10 -> ee_attack_s4 = equipment; // update attack slot 4
                case 11 -> ee_attack_s5 = equipment; // update attack slot 5
                case 12 -> ee_attack_s6 = equipment; // update attack slot 6
                case 13 -> ee_attack_s7 = equipment; // update attack slot 7
                case 14 -> ee_damage_s1 = equipment; // update damage slot 1
                case 15 -> ee_damage_s2 = equipment; // update damage slot 2
                case 16 -> ee_damage_s3 = equipment; // update damage slot 3
                case 17 -> ee_damage_s4 = equipment; // update damage slot 4
                case 18 -> ee_damage_s5 = equipment; // update damage slot 5
                case 19 -> ee_damage_s6 = equipment; // update damage slot 6
                case 20 -> ee_damage_s7 = equipment; // update damage slot 7
            }
        }
    }

    // Currencies

    /**
     * Sets currencies as passed array
     * @param values value
     */
    public void setCurrencies(int[] values){
        this.currencies = values; // set array directly
        this.cp = values[0]; // update copper
        this.sp = values[1]; // update silver
        this.gp = values[2]; // update gold
        this.pp = values[3]; // update platinum
    }

    /**
     * Sets currency at position as passed value
     * @param index position
     * @param value value
     */
    public void setCurrencies(int index, String value){
        try {
            int val = Integer.parseInt(value); // attempt to parse value
            if(index >= 0 && index < currencies.length){
                currencies[index] = val; // update array
                switch(index){
                    case 0 -> cp = val; // update copper
                    case 1 -> sp = val; // update silver
                    case 2 -> gp = val; // update gold
                    case 3 -> pp = val; // update platinum
                }
            }
        } catch(NumberFormatException ignore){}
    }

    // Misc

    /**
     * Sets misc as passed array
     * @param misc misc
     */
    public void setMisc(String[] misc){
        this.misc = misc; // set array
        this.passives = misc[0]; // update passive string
    }

    /**
     * Sets misc value at position as passed value
     * @param index position
     * @param misc value
     */
    public void setMisc(int index, String misc){
        if(index >= 0 && index < this.misc.length){
            this.misc[index] = misc; // set specific entry
            if(index == 0){
                this.passives = misc; // update passive field
            }
        }
    }
}
