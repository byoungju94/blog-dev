export default class BodyComponents {

    private number: number = 0;

    constructor() {
        this.number = 1;
    }

    public test() {
        console.log("number: ", this.number);
    }
}