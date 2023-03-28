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

  mainMenu() {
    do {
      console.log('Write action (buy, fill, take, remaining, exit):');
      let choice = input();
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
    console.log(
      '\nWhat do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, 4 - custom, back - to main menu:'
    );
    let coffeeChoice = input();
    console.log('How much sugar?');
    let sugar = Number(input());

    let size;
    switch (coffeeChoice) {
      case '1':
        size = this.cupChoice();
        let espresso = new Coffee('espresso', size, sugar);
        this.makeCoffee(espresso);
        break;
      case '2':
        size = this.cupChoice();
        let latte = new Coffee('latte', size, sugar);
        this.makeCoffee(latte);
        break;
      case '3':
        size = this.cupChoice();
        let cappuccino = new Coffee('cappuccino', size, sugar);
        this.makeCoffee(cappuccino);
        break;
      case '4':
        size = this.cupChoice();
        console.log('Write how many ml of water you want to add:');
        let water = Number(input());
        console.log('Write how many ml of milk you want to add:');
        let milk = Number(input());
        console.log('Write how many grams of coffee beans you want to add:');
        let beans = Number(input());
        let customCoffee = new CustomCoffee(water, milk, beans);
        let custom = new Coffee('custom', size, sugar, customCoffee);
        this.makeCoffee(custom);
        break;
      case 'back':
        break;
      default:
        break;
    }
  }

  makeCoffee(coffee) {
    let ingredientsNeeded = coffee.getIngredientsNeeded();
    let cup = this.cups.find((cup) => cup.size === coffee.size);
    if (this.checkIngredients(ingredientsNeeded, cup, coffee.sugar)) {
      this.updateIngredients(ingredientsNeeded, cup, coffee.price);
    }
  }

  cupChoice() {
    console.log('What size? (S/M/L):');
    do {
      let cupSize = input().toUpperCase();
      switch (cupSize) {
        case 'S':
          return 'small';
        case 'M':
          return 'medium';
        case 'L':
          return 'large';
        default:
          console.log('Invalid cup choice!');
      }
    } while (true);
  }

	fillMachine() {
		console.log('Write how many ml of water you want to add:');
		let water = Number(input());
		console.log('Write how many ml of milk you want to add:');
		let milk = Number(input());
		console.log('Write how many grams of coffee beans you want to add:');
		let beans = Number(input());
		console.log('Write how many disposable cups you want to add (S):');
		let cupS = Number(input());
		console.log('Write how many disposable cups you want to add (M):');
		let cupM = Number(input());
		console.log('Write how many disposable cups you want to add (L):');
		let cupL = Number(input());

		this.ingredients.water += water;
		this.ingredients.milk += milk;
		this.ingredients.beans += beans;
		this.cups.find((cup) => cup.size === 'small').amount += cupS;
		this.cups.find((cup) => cup.size === 'medium').amount += cupM;
		this.cups.find((cup) => cup.size === 'large').amount += cupL;
  }

  checkIngredients(ingredientsNeeded, cup, sugar) {
    if (ingredientsNeeded.water > this.ingredients.water) {
      console.log('Sorry, not enough water!\n');
      return false;
    }
    if (ingredientsNeeded.milk > this.ingredients.milk) {
      console.log('Sorry, not enough milk!\n');
      return false;
    }
    if (ingredientsNeeded.beans > this.ingredients.beans) {
      console.log('Sorry, not enough beans!\n');
      return false;
    }
    if (cup.amount === 0) {
      console.log(`Sorry, not enough ${cup.size} cups!\n`);
      return false;
    }
    if (sugar > this.sugarLevel) {
      console.log('Sorry, not enough sugar!\n');
      return false;
    }
    console.log(`I have enough resources, making you a coffee!\n`);
    return true;
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
    switch (this.type) {
      case 'espresso':
        this.ingredients = new Ingredients(250, 0, 16, 4);
        this.price = 1.5;
        break;
      case 'latte':
        this.ingredients = new Ingredients(350, 75, 20, 7);
        this.price = 2.5;
        break;
      case 'cappuccino':
        this.ingredients = new Ingredients(200, 100, 12, 6);
        this.price = 3;
        break;
      case 'custom':
        this.ingredients = new Ingredients(
          this.customCoffee.water,
          this.customCoffee.milk,
          this.customCoffee.beans,
          0
        );
        this.price = 3;
        break;
      default:
        break;
    }
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