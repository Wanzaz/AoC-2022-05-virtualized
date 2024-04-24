# AoC-2022-05-virtualized
Semester project focusing on virtualization of AoC 2022 task 05 with GUI app

# Obsah
1. Zadání
    * 1.1 Požadavky
    * 1.2 Zadání ročníkové práce
    * 1.3 Zadání úlohy Advent of Code
2. Analýza zadání
3. Návrh řešení
4. Popis implementace
5. Závěr

## 1 Zadání

### 1.1 Požadavky
Vytvoř program pro zpracování souboru s daty

Splnit body
* Dokumentace projektu
* Zpracování souboru
* Detekce chyb v souboru
* Dokumentační komentáře

### 1.2 Zadání ročníkové práce
Tématem této ročníkové práce bude analyzovat řešení úlohy Advent of Code 2022 s označením 05. Cílem bude předvést co neefektivnější řešení pro tento problém, porovnat ho s ostatníma řešeními, které jsou běžně člověku dostupné na internetu. Nadále vytvořit Graphical User Interface (GUI), aby užival měl větší kontrolu nad aplikací a měl tu možnost zasahovat do běhu algoritmu. A navíce uživali tento úkol bude vykreslen pomocí GUI, pro lepší pochopení běhu algoritmu.
 
### 1.3 Zadání úlohy Advent of Code

```py
--- Day 5: Supply Stacks ---
```

The expedition can depart as soon as the final supplies have been unloaded from the ships. Supplies are stored in stacks of marked crates, but because the needed supplies are buried under many other crates, the crates need to be rearranged.
The ship has a giant cargo crane capable of moving crates between stacks. To ensure none of the crates get crushed or fall over, the crane operator will rearrange them in a series of carefully-planned steps. After the crates are rearranged, the desired crates will be at the top of each stack.

The Elves don't want to interrupt the crane operator during this delicate procedure, but they forgot to ask her which crate will end up where, and they want to be ready to unload them as soon as possible so they can embark.

They do, however, have a drawing of the starting stacks of crates and the rearrangement procedure (your puzzle input). For example:
```py
    [D]    
[N] [C]    
[Z] [M] [P]
 1   2   3 

move 1 from 2 to 1
move 3 from 1 to 3
move 2 from 2 to 1
move 1 from 1 to 2
```

In this example, there are three stacks of crates. Stack 1 contains two crates: crate Z is on the bottom, and crate N is on top. Stack 2 contains three crates; from bottom to top, they are crates M, C, and D. Finally, stack 3 contains a single crate, P.

Then, the rearrangement procedure is given. In each step of the procedure, a quantity of crates is moved from one stack to a different stack. In the first step of the above rearrangement procedure, one crate is moved from stack 2 to stack 1, resulting in this configuration:
```py
[D]        
[N] [C]    
[Z] [M] [P]
 1   2   3 
```
In the second step, three crates are moved from stack 1 to stack 3. Crates are moved one at a time, so the first crate to be moved (D) ends up below the second and third crates:

```py
        [Z]
        [N]
    [C] [D]
    [M] [P]
 1   2   3
```

Then, both crates are moved from stack 2 to stack 1. Again, because crates are moved one at a time, crate C ends up below crate M:
```py
        [Z]
        [N]
[M]     [D]
[C]     [P]
 1   2   3
```

Finally, one crate is moved from stack 1 to stack 2:
```py
        [Z]
        [N]
        [D]
[C] [M] [P]
 1   2   3
```

The Elves just need to know which crate will end up on top of each stack; in this example, the top crates are C in stack 1, M in stack 2, and Z in stack 3, so you should combine these together and give the Elves the message CMZ.

**After the rearrangement procedure completes, what crate ends up on top of each stack?**

-------
-------
-------

# 2 Analýza problému
Nejdříve je potřeba zamyslet, jak tuto komplexní úlohu řešit (dvakrát měř jednou řež), protože řešení je to hlavní, co tuto ročníkovou práci definuje. Nadále je nutné vytvořit hiearchii pro efektivní řešení této aplikace. 

Také je šikovné si ideálně vytvořit obecnou třídu, která bude řešit jednoduché problémy pomocí pomocných funkcí (např. transformace souboru do textového řetězce) a postupně zvyšovat složitost. 

# 3 Návrh řešení
Tady popiš, jak navrhuješ řešit identifikované problémy. Vybírej z návrhů a zdůvodňuj jejich výběr.
# 4 Popis implementace
Tady stručně popiš své konkrétní řešení podle návrhů vybraných a odůvodněných v minulé části.

