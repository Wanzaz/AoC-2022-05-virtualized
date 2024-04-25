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
Nejdříve je potřeba zamyslet, jak tuto komplexní úlohu řešit (dvakrát měř jednou řež), protože řešení je to hlavní, co tuto ročníkovou práci definuje. Velká část úlohy se týká načítání a zpracovávání dat. Soubor je dost specifický a komplexní pro načítání, takže se dá očekávat, že bude spousta výjimek. Protože jak je známo, uživatelé jsou velmi kreatívní při zadávání vstupu. Nadále je nutné vytvořit hiearchii pro efektivní řešení této aplikace. 

Také je šikovné si ideálně vytvořit obecnou třídu, která bude řešit jednoduché problémy pomocí pomocných funkcí (např. transformace souboru do textového řetězce) a postupně zvyšovat složitost. 

# 3 Návrh řešení
#### Algoritmus
Pro řešení tohoto problému mě napadly dva způsoby, jak ho vyřešit. První by bylo to řešit přes velmi známou strukturu jménem pole, nadále mě napadlo to řešit přes více specifickou datovou strukturu jménem zásobník. Tato struktura  vyžaduje trochu více kreativní přístup, který na první pohled není tak zřejmý. V tomto případě se určitě vyplatí, protože řešení s polem by bylo celkem dost chaotické a měl vyšší složitost. Pro přesvědčení si tyto přístupy porovnáme v pozdější části. Moje řešení tedy bude obsahovat zásobník a pro kontrast porovnáme i internetové řešení s polem.

#### Instrukce
Pro část s instrukcemi by se dal použít regex, který jsme se zrovna učili. Také by se dala implementovat struktura record, která by nám umožnila zefektivnit řešení, protože nám uštří jednu celou třídu.

#### Výjimky
Nadále si vytvoříme autentické třídy výjimek, abychom mohli uživatele informovat, kde přesně jeho input zhavaroval. Dá se počítat s tím, že to je velké předsevzetí, aby uživatel byl správně informován ohledně jeho souboru. 

#### Obecná třída
Dále tu máme obecnou třídu, která bude mít pomocné funkce, která budou zásadně dělat jen daný účel. Abychom zachovali atomizaci funkcí a tím zvýšili efektivnost aplikace.

# 4 Popis implementace
#### Algoritmus
Pro realizaci algoritmu jsme ho rozdělili na 6 částí.

#### 1.1 Transformace dat ze souboru
V první části jsme načetli uživatelská data pomocí funkce ```readFileAsListOfStrings```, která je součástí obecné třídy pod názvem ```Utilities```. Důvod je ten, abychom se zbytečně v dalších krocích nevraceli do souboru.
#### 1.2 Prázdný řádek
V druhé části jsme našli mezi daty prázdný řádek, který je jedním z autentických znaků této úlohy. Udělali jsme to pomocí funkce ```findBlankIndex```, která je také součástí třídy ```Utilities```. Tato funkce nám vrací index prázdného řádku, abychom se podle něho mohli orientovat v "souboru" (už je to pole s typem String).
#### 1.3 Načtení beden
V třetí části načítáme tu skládanku z beden. Orientujeme se pomocí prázdného řádku, protože nad prázndným řádkem nacházíme očíslované sloupce beden. Očíslování nám pomůýe k tomu, abychom se dostali k tě bednám samým. Tento proces se děje ve funkci ```initializeStacks```. Nadále postupujeme bedna po bedně a načítáme je do toho aktuálního sloupce. Avšak jakmile narazíme na "prázdnou bednu", tak to znamená, že jsme na konci sloupce a posuneme se k dalšímu sloupci. K tomuto procesu nám dopomáhá struktura zásobníku a její jedinečné znalosti. Nakonec vrátíme pole zásobníku s datovým typem charakter.
#### 1.4 Načtení instrukcí
V čtrté části používáme slíbený regex, který má generický formát pro načítaní instrukcí. I v tomto případě se orientujeme podle prázdného řádku, protože instrukce se nacházejí pod prazdným řádkem. Používám zde i record ```Instruction```, který obsahuje počet beden, které chtějí být přesunuty, sloupec ze kterého se mají být bráný a cílový sloupec, kam se přesunou. Používáme zde známé operace nad zásobníkem jako je push a pop k splnění tohoto úkolu, který obsahuje počet beden, které chtějí být přesunuty, sloupec ze kterého se mají být bráný a cílový sloupec, kam se přesunou. Používáme zde známé operace nad zásobníkem jako je push a pop k splnění tohoto úkolu. Tento proces se odehrává ve funkci s názvem ```processInstructions```. Je to procedúra, takže nic nevracíme.
#### 1.5 Získání výsledku
V páté části používáme primitivní funkci s názvem ```getResult```, která projde všechny sloupce a zaznamená vrchní hodnoty zásobníku, které se uloží do řetězce. Tento řetězec je následně vrácen.
#### 1.1 Obalovací funkce
V šesté části toho algoritmu se dostáváme s samému konci a to k obalovací fuknci ```day_05_1```. Tato funkce zastřešuje popsaný průběh algoritmu a vrací výsledek zadaného souboru.

#### Výjimky
Pro výjimky byly vytvořeny dvě třídy ```InputDataException``` ```InputFormatException```, které mají nastarost informovanost uživatele. Jsou vyhazovány v načítacích funkcích, kde je možné narazit na problémy. Například běžné problémy jsou:
- nesprávný index
- duplikace názvu beden
- špatné očíslovaní sloupce
- prázdný sloupec
- příliš vysoký počet beden, který daný sloupec neobsahuje
- nespravný formát instrukce

Dále tu máme běžnou vyjímku jako například ```IOException```.

