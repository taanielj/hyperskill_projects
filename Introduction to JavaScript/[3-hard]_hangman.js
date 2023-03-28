// Use "input()" to input a line from the user
// Use "input(str)" to print some text before requesting input
// You will need this in the following stages
const input = require('sync-input');



greeting();
mainMenu()


function greeting() {
    console.log(`H A N G M A N`);
}

function mainMenu(){
    let keepPlaying = true;
    let wins = 0;
    let losses = 0;
    let result;
    do{
        let menuOption = input("Type \"play\" to play the game, \"results\" to show the scoreboard, and \"exit\" to quit: ")
        switch (menuOption) {
            case "play":
                result = guessTheWord()
                if (result === 1) {
                    wins++
                } else {
                    losses++
                }
                break;
            case "results":
                console.log(`You won: ${wins} times.`)
                console.log(`You lost: ${losses} times.`)
                break;
            case "exit":
                keepPlaying = false;
                break;
            default:
                break

        }
    } while (keepPlaying === true);
    if (keepPlaying === false) {
        process.exit()
    }
}

function guessTheWord() {
    let attempts = 8;
    const correctWord = randomWord();
    let guessedWord = correctWord.replace(/./g,"-").split("");
    let guessedLetters = [];
    let result;
    do {
        console.log("\n" + guessedWord.join(""));
        let prompt = `Input a letter: `;
        let letter = input(prompt);
        let isLetter = false;
        if (letter.length !== 1){
            console.log("Please, input a single letter.");
        } else if (guessedLetters.join("").indexOf(letter) !==-1) {
            console.log("You've already guessed this letter.");
        } else if (!(/^[a-z]*$/.test(letter))) {
            console.log("Please, enter a lowercase letter from the English alphabet.")
        } else {
            guessedLetters.push(letter)
            for(let i in guessedWord) {
                if (letter === correctWord.split("")[i]) {
                    guessedWord[i] = letter;
                    isLetter = true;
                }
            }
            if (isLetter === false) {
                console.log("That letter doesn't appear in the word.")
                attempts--;
            }
        }
        if (attempts === 0){
            console.log(`\nYou lost!`)
            result = 0;
        }
        if (guessedWord.join("") === correctWord){
            attempts = 0;
            console.log(`\nYou guessed the word ${correctWord}!\nYou survived!`)
            result = 1;
        }
    } while (attempts !== 0);
    return result;
}

function randomWord(){
    let wordList = ["python", "java", "swift", "javascript"]
    return wordList[Math.floor(Math.random()*4)]
}

