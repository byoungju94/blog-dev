import React from "react";

interface Category {
    id: string,
    name: string
}

export default class BodyComponents {
    private props: Category;

    constructor(props: Category) {
        this.props = props;
    }

    public render() {
        return (
            <div 
                className="category" 
                data-category-id={this.props.id}
                data-category-name={this.props.name}
            >
                {`${this.props.id}/${this.props.name}`}
            </div>
        );
    }
}

