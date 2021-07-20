import Category from "../../domain/category/Category";

export default class Top {

    private readonly categories: Array<Category>
    private categoryElement: HTMLUListElement;

    constructor(categories: Array<Category>) {
        this.categories = categories;
        this.categoryElement = document.createElement('ul');
        this.categoryElement.className = "list-group list-group-horizontal";

        this.renderHorizontalListGroup();
    }

    private renderHorizontalListGroup() {
        this.categories.map(category => {
            const li = document.createElement("li");
            li.className = "list-group-item";
            li.dataset.id = category.id;
            li.textContent = category.name;
            return li;
        }).forEach(element => {
            this.categoryElement.appendChild(element);
        });        
    }

    getElement() {
        const div = document.createElement("div");
        div.className = "container";
        div.appendChild(this.categoryElement);
        return div;
    }
}