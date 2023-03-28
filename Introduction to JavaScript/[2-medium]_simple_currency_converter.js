const input = require('sync-input');

const conversion_list =  {
    USD: 1.0,
    JPY: 113.5,
    EUR: 0.89,
    RUB: 74.36,
    GBP: 0.75
};

console.log(`Welcome to Currency Converter!
1 USD equals 1 USD
1 USD equals 113.5 JPY
1 USD equals 0.89 EUR
1 USD equals 74.36 RUB
1 USD equals 0.75 GBP`);

let inCurrency, outCurrency, inAmount, outAmount, whatDo;

do {
    console.log(`What do you want to do?\n1-Convert currencies 2-Exit program`);
    whatDo = input();
    switch(whatDo) {
        case '1':
            console.log("What do you want to convert?")
            inCurrency = promptCurrency("From: ");
            outCurrency = promptCurrency("To: ");
            inAmount = promptAmount();
            if (inAmount) {
                convert();
            }
            break;
        case '2':
            console.log("Have a nice day!");
            break;
        default:
            console.log("Unknown input");
    }
} while (whatDo !== '2');

function promptCurrency(text) {
    let currency;
    do {
        currency = input(text).toUpperCase();
        if (!(currency in conversion_list)) {
            console.log("Unknown currency");
        }
    } while (!(currency in conversion_list));
    return currency;
}

function promptAmount() {
    let amount;
    let isValid = false;
    do {
        amount = parseFloat(input("Amount: "));
        switch (true) {
            case isNaN(amount):
                console.log("The amount has to be a number");
                break;
            case amount < 1:
                console.log("The amount cannot be less than 1");
                break;
            default:
                isValid = true;
                break;
        }
    } while (!isValid);
    return amount;
}

function convert() {
    outAmount = (inAmount * conversion_list[outCurrency] / conversion_list[inCurrency]).toFixed(4);
    console.log(`Result: ${inAmount} ${inCurrency} equals ${outAmount} ${outCurrency}`);
}
