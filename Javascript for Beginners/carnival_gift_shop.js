let input = require('sync-input');

let gifts = [
    { name: "Teddy Bear", price: 10, id: 1 },
    { name: "Big Red Ball", price: 5, id: 2 },
    { name: "Huge Bear", price: 50, id: 3 },
    { name: "Candy", price: 8, id: 4 },
    { name: "Stuffed Tiger", price: 15, id: 5 },
    { name: "Stuffed Dragon", price: 30, id: 6 },
    { name: "Skateboard", price: 100, id: 7 },
    { name: "Toy Car", price: 25, id: 8 },
    { name: "Basketball", price: 20, id: 9 },
    { name: "Scary Mask", price: 75, id: 10 }
]
console.log(`WELCOME TO THE CARNIVAL GIFT SHOP!
Hello friend! Thank you for visiting the carnival!`);
showGifts();

let userChoice;
let userTickets = 0;
do {
    console.log(`\nWhat do you want to do?`);
    console.log(`1-Buy a gift 2-Add tickets 3-Check tickets 4-Show gifts 5-Exit the shop`);
    userChoice = Number(input());
    switch (userChoice){
        case 1:
            buyGift();
            showTickets();
            break;
        case 2:
            addTickets();
            showTickets();
            break;
        case 3:
            showTickets();
            break;
        case 4:
            showGifts();
            break;
        case 5:
            console.log("Have a nice day!");
            process.exit();
            break;
        default:
            console.log("Please enter a valid number!")
    }
} while(true);

function showGifts(){
    console.log(`Here's the list of gifts:\n`);
    for (let i of gifts){
        console.log(`${i.id}- ${i.name}, Cost: ${i.price} tickets`);
    }
}

function showTickets(){
    console.log(`Total tickets: ${userTickets}`);
}

function buyGift(){
    if (gifts.length === 0){
        console.log("Wow! There are no gifts to buy.");
    } else {
        let userGift = Number(input("Enter the number of the gift you want to get: "));
        if (isNaN(userGift)){
            console.log("Please enter a valid number!")
        } else {
            if (gifts.findIndex(e => e.id === userGift) === -1) {
                console.log("There is no gift with that number!")
            } else {
                let gift = gifts.find(e => e.id === userGift);
                if (userTickets >= gift.price){
                    userTickets = userTickets - gift.price;
                    console.log(`Here you go, one ${gift.name}!`);
                    gifts.splice(gifts.findIndex(e => e.id === userGift),1);
                } else {
                    console.log("You don't have enough tickets to buy this gift.")
                }

            }
        }

    }
}

function addTickets(){
    let userBuyTickets = input("Enter the ticket amount: ");
    if (userBuyTickets >= 0 && userBuyTickets <= 1000){
        userTickets = userTickets + Number(userBuyTickets);
    } else {
        console.log("Please enter a valid number between 0 and 1000.")
    }
}
