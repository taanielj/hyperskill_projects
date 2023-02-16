const input = require('sync-input');
console.log("Welcome to Currency Converter!")

let conversion_list =  {
    USD: 1.0,
    JPY: 113.5,
    EUR: 0.89,
    RUB: 74.36,
    GBP: 0.75
};

for (let i in conversion_list){
    console.log(`1 USD equals ${conversion_list[i]} ${i}`);
}

console.log(`I can convert USD to these currencies: \
JPY, EUR, RUB, USD, GBP
Type the currency you wish to convert: USD`);

process.stdout.write('To: ');
let inCurrency = "USD"
let outCurrency = input().toUpperCase()
switch(outCurrency) {
    case "USD":
    case "JPY":
    case "EUR":
    case "RUB":
    case "GBP":
        process.stdout.write('Amount: ');
        let inAmount = input()
        if (inAmount < 1){
            console.log("The amount cannot be less than 1")
        } else if ( isNaN(inAmount) ) {
            console.log("The amount has to be a number")
        } else {
            let outAmount = (inAmount / conversion_list[inCurrency] * conversion_list[outCurrency]).toFixed(4)
            console.log(`Result: ${inAmount} ${inCurrency} equals ${outAmount} ${outCurrency} `);
        }
        break
    default:
        console.log("Unknown currency")
}
