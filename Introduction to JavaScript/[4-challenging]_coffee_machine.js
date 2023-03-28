const input = require('sync-input')

const ingEspresso = new Ingredients(250, 0, 16, 4);
const ingLatte = new Ingredients(350, 75,20, 7);
const ingCappuccino = new Ingredients(200, 100, 12,6);
let ingCurrent = new Ingredients(400, 540, 120,550);
let customCoffee = new Ingredients(0,0,0,8)
let sugarLevel = 500

let cupSmall = new Cup(300, 9)
let cupMedium = new Cup(450, 9)
let cupLarge = new Cup(675, 9)
let custom

mainMenu();

function Ingredients(water, milk, beans, money) {
    this.water = water;
    this.milk = milk;
    this.beans = beans;
    this.money = money;
}
function Cup (size, amount) {
    this.size = size;
    this.amount = amount;
}

function mainMenu() {
    do {
        console.log("Write action (buy, fill, take, remaining, exit):");
        let choice = input();
        switch(choice){
            case "buy":
                buyCoffee();
                break;
            case "fill":
                fillMachine();
                break;
            case "take":
                takeMoney();
                break;
            case "remaining":
                showLevels();
                break;
            case "exit":
                return;
            default:
                break;
        }
    } while (true);
}

function buyCoffee() {
    console.log(`\nWhat do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, 4 - custom, back - to main menu: `);
    let coffeeChoice = input()
    console.log("How much sugar?")
    let sugar = Number(input())
    console.log(sugar)
    console.log(typeof sugar)
    let size;
    custom = false;
    switch (coffeeChoice){
        case "1":
            size = cupChoice();
            if (checkIngredients(ingEspresso, size, sugar)){
                updateIngredients(ingEspresso, size, sugar);
            }
            break;
        case "2":
            size = cupChoice();
            if (checkIngredients(ingLatte, size, sugar)){
                updateIngredients(ingLatte, size, sugar);
            }
            break;
        case "3":
            size = cupChoice();
            if (checkIngredients(ingCappuccino, size, sugar)){
                updateIngredients(ingCappuccino, size, sugar);
            }
            break;
        case "4":
            custom = true;
            size = cupChoice();
            console.log("Write how many ml of water you want to add: ");
            let water = Number(input());
            console.log("Write how many ml of milk you want to add: ");
            let milk = Number(input());
            console.log("Write how many grams of coffee beans you want to add:");
            let beans = Number(input());
            customCoffee.water = water;
            customCoffee.milk = milk;
            customCoffee.beans = beans;
            if (checkIngredients(customCoffee, size, sugar)){
                updateIngredients(customCoffee, size, sugar);
            }
            break;
        case "back":
            break;
        default:
            break;
    }
}

function cupChoice (){
    console.log("What size? (S/M/L): ")
    do {
        let cupSize = input().toUpperCase()
        switch (cupSize){
            case "S":
                return cupSmall;
            case "M":
                return cupMedium;
            case "L":
                return cupLarge;
            default:
                console.log("Invalid cup choice!")
        }
    } while (true)
}

function fillMachine(){
    console.log("Write how many ml of water you want to add: ");
    let water = Number(input());
    console.log("Write how many ml of milk you want to add: ");
    let milk = Number(input());
    console.log("Write how many grams of coffee beans you want to add:");
    let beans = Number(input());
    console.log("Write how many disposable cups you want to add (S):");
    let cupS = Number(input());
    console.log("Write how many disposable cups you want to add (M):");
    let cupM = Number(input());
    console.log("Write how many disposable cups you want to add (L):");
    let cupL = Number(input());
    ingCurrent.water += water;
    ingCurrent.milk += milk;
    ingCurrent.beans +=  beans;
    cupSmall.amount += cupS;
    cupMedium.amount += cupM;
    cupLarge.amount += cupL;
}
function checkIngredients(coffeeType, cupChoice, sugar) {
    let enough = false;
    let sizeRatio = 1;
    if (custom === false) {
        sizeRatio = cupChoice.size / cupMedium.size
    }
    let totalVolume = coffeeType.water*sizeRatio + coffeeType.milk*sizeRatio + sugar
        if (custom === false){
        totalVolume = totalVolume * sizeRatio
    }
    if (totalVolume > cupChoice.size){
        console.log("Sorry, cup too small!")
        console.log(`(Total volume: ${totalVolume} mL, cup volume: ${cupChoice.size} mL`)
    }
    if (ingCurrent.water < coffeeType.water*sizeRatio) {
        console.log("Sorry, not enough water!\n");
    } else if (ingCurrent.milk < coffeeType.water*sizeRatio) {
        console.log("Sorry, not enough milk!");
    } else if (ingCurrent.beans < coffeeType.beans*sizeRatio) {
        console.log("Sorry, not enough beans!\n");
    } else if (cupChoice.amount === 0) {
        console.log("Sorry, not enough cups of this size!\n");
    } else if (sugar > sugarLevel) {
        console.log("Sorry, not enough sugar!\n");
    } else {
        console.log(`I have enough resources, making you a coffee!\n`)
        enough = true;
    }
    return enough;
}
function updateIngredients(coffeeType, cupChoice, sugar) {
    let sizeRatio = 1;
    if (custom === false) {
        sizeRatio = cupChoice.size / cupMedium.size
    }
    ingCurrent.water -= coffeeType.water*sizeRatio;
    ingCurrent.milk -= coffeeType.milk*sizeRatio;
    ingCurrent.beans -= coffeeType.beans*sizeRatio;
    ingCurrent.money += coffeeType.money*sizeRatio;
    cupChoice.amount--;
    if (typeof sugar === "number") {
        sugarLevel -= sugar
    }
}


function showLevels() {
    console.log(`\nThe coffee machine has:\n` +
        `${ingCurrent.water.toFixed(2)} ml of water\n` +
        `${ingCurrent.milk.toFixed(2)} ml of milk\n` +
        `${ingCurrent.beans.toFixed(2)} g of coffee beans\n` +
        `${sugarLevel.toFixed(2)} g of sugar\n` +
        `${cupSmall.amount} small disposable cups\n` +
        `${cupMedium.amount} medium disposable cups\n` +
        `${cupLarge.amount} large disposable cups\n` +
        `$${ingCurrent.money} of money\n`);
}



function takeMoney(){
    console.log(`I gave you $${550}`)
    ingCurrent.money = 0
}