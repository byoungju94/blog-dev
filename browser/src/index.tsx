import Category from './domain/category/Category';
import './index.css';
import Top from './layout/top/Top';

const categories = [];
categories.push(new Category("UFHWNVOW123", "JAVA"));
categories.push(new Category("UFHWNVOW123", "SpringFramework"));
categories.push(new Category("UFHWNVOW123", "MySQL"));

const layoutTop = new Top(categories);
document.getElementById('root')?.appendChild(layoutTop.getElement());
