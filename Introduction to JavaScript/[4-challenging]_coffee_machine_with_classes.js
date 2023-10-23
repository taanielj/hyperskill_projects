const input = require('sync-input');

class CoffeeMachine {
  constructor() {
    this.ingredients = new Ingredients(400, 540, 120, 550);
    this.cups = [
      new Cup('small', 9),
      new Cup('medium', 9),
      new Cup('large', 9),
    ];
    this.sugarLevel = 500;
  }

  prompt(message) {
    console.log(message);
    return input();
  }

  promptNumber(message) {
    return Number(this.prompt(message));
  }

  mainMenu() {
    let choice;
    do {
      choice = this.prompt('Write action (buy, fill, take, remaining, exit):');
      switch (choice) {
        case 'buy':
          this.buyCoffee();
          break;
        case 'fill':
          this.fillMachine();
          break;
        case 'take':
          this.takeMoney();
          break;
        case 'remaining':
          this.showLevels();
          break;
        case 'exit':
          return;
        default:
          break;
      }
    } while (true);
  }

  buyCoffee() {
    let coffeeChoice = this.prompt('\nWhat do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, 4 - custom, back - to main menu:');
    let sugar = this.promptNumber('How much sugar?');
    let size = this.cupChoice();

    if (coffeeChoice === 'back') return;

    let coffee;
    if (coffeeChoice === '4') {
      coffee = new Coffee('custom', size, sugar, new CustomCoffee(
        this.promptNumber('Write how many ml of water you want to add:'),
        this.promptNumber('Write how many ml of milk you want to add:'),
        this.promptNumber('Write how many grams of coffee beans you want to add:')
      ));
    } else {
      const types = ['espresso', 'latte', 'cappuccino'];
      coffee = new Coffee(types[coffeeChoice - 1], size, sugar);
    }
    this.makeCoffee(coffee);
  }

  cupChoice() {
    let sizes = { 'S': 'small', 'M': 'medium', 'L': 'large' };
    let cupSize;
    do {
      cupSize = this.prompt('What size? (S/M/L):').toUpperCase();
    } while (!sizes[cupSize]);
    return sizes[cupSize];
  }

  fillMachine() {
    const additions = {
      water: 'Write how many ml of water you want to add:',
      milk: 'Write how many ml of milk you want to add:',
      beans: 'Write how many grams of coffee beans you want to add:',
      small: 'Write how many disposable cups you want to add (S):',
      medium: 'Write how many disposable cups you want to add (M):',
      large: 'Write how many disposable cups you want to add (L):'
    };

    for (let [key, message] of Object.entries(additions)) {
      let amount = this.promptNumber(message);
      if (['small', 'medium', 'large'].includes(key)) {
        this.cups.find(cup => cup.size === key).amount += amount;
      } else {
        this.ingredients[key] += amount;
      }
    }
  }

  checkIngredients(ingredientsNeeded, cup, sugar) {
    const ingredientMap = {
      water: 'Sorry, not enough water!\n',
      milk: 'Sorry, not enough milk!\n',
      beans: 'Sorry, not enough beans!\n',
      cup: `Sorry, not enough ${cup.size} cups!\n`,
      sugar: 'Sorry, not enough sugar!\n'
    };

    for (let ingredient in ingredientMap) {
      if (ingredient === 'cup' && cup.amount === 0) {
        console.log(ingredientMap[ingredient]);
        return false;
      } else if (ingredientsNeeded[ingredient] && ingredientsNeeded[ingredient] > this.ingredients[ingredient]) {
        console.log(ingredientMap[ingredient]);
        return false;
      }
    }

    console.log(`I have enough resources, making you a coffee!\n`);
    return true;
  }

  makeCoffee(coffee) {
    let ingredientsNeeded = coffee.getIngredientsNeeded();
    let cup = this.cups.find((cup) => cup.size === coffee.size);
    if (this.checkIngredients(ingredientsNeeded, cup, coffee.sugar)) {
      this.updateIngredients(ingredientsNeeded, cup, coffee.price);
    }
  }

  updateIngredients(ingredientsNeeded, cup, price) {
    this.ingredients.water -= ingredientsNeeded.water;
    this.ingredients.milk -= ingredientsNeeded.milk;
    this.ingredients.beans -= ingredientsNeeded.beans;
    this.sugarLevel -= ingredientsNeeded.sugar;
    cup.amount--;
    this.ingredients.money += price;
  }

  showLevels() {
    console.log(
      `\nThe coffee machine has:\n` +
      `${this.ingredients.water.toFixed(2)} ml of water\n` +
      `${this.ingredients.milk.toFixed(2)} ml of milk\n` +
      `${this.ingredients.beans.toFixed(2)} g of coffee beans\n` +
      `${this.sugarLevel.toFixed(2)} g of sugar\n` +
      `${this.cups.find((cup) => cup.size === 'small').amount} small disposable cups\n` +
      `${this.cups.find((cup) => cup.size === 'medium').amount} medium disposable cups\n` +
      `${this.cups.find((cup) => cup.size === 'large').amount} large disposable cups\n` +
      `$${this.ingredients.money.toFixed(2)} of money\n`
    );
  }

  takeMoney() {
    console.log(`I gave you $${this.ingredients.money.toFixed(2)}`);
    this.ingredients.money = 0;
  }
}

class Ingredients {
  constructor(water, milk, beans, money) {
    this.water = water;
    this.milk = milk;
    this.beans = beans;
    this.money = money;
  }
}

class Cup {
  constructor(size, amount) {
    this.size = size;
    this.amount = amount;
  }
}

class Coffee {
  constructor(type, size, sugar, customCoffee = null) {
    this.type = type;
    this.size = size;
    this.sugar = sugar;
    this.customCoffee = customCoffee;
    this.setIngredients();
  }

  setIngredients() {
    const coffeeTypes = {
      'espresso': { ingredients: new Ingredients(250, 0, 16, 4), price: 1.5 },
      'latte': { ingredients: new Ingredients(350, 75, 20, 7), price: 2.5 },
      'cappuccino': { ingredients: new Ingredients(200, 100, 12, 6), price: 3 },
      'custom': { ingredients: new Ingredients(this.customCoffee.water, this.customCoffee.milk, this.customCoffee.beans, 0), price: 3 }
    };
    this.ingredients = coffeeTypes[this.type].ingredients;
    this.price = coffeeTypes[this.type].price;
  }

  getIngredientsNeeded() {
    let ingredientsNeeded = new Ingredients(
      this.ingredients.water,
      this.ingredients.milk,
      this.ingredients.beans,
      0
    );
    let sizeRatio = 1;
    switch (this.size) {
      case 'small':
        sizeRatio = 0.75;
        break;
      case 'medium':
        sizeRatio = 1;
        break;
      case 'large':
        sizeRatio = 1.25;
        break;
      default:
        break;
    }
    ingredientsNeeded.water *= sizeRatio;
    ingredientsNeeded.milk *= sizeRatio;
    ingredientsNeeded.beans *= sizeRatio;
    ingredientsNeeded.sugar = this.sugar;
    return ingredientsNeeded;
  }
}

class CustomCoffee {
  constructor(water, milk, beans) {
    this.water = water;
    this.milk = milk;
    this.beans = beans;
  }
}

const coffeeMachine = new CoffeeMachine();
coffeeMachine.mainMenu();
