import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import Bottom from './layout/bottom/Bottom';
import Middle from './layout/middle/Middle';
import Top from './layout/top/Top';
import reportWebVitals from './reportWebVitals';

ReactDOM.render([<Top />, <Middle />, <Bottom />], document.getElementById('root'));

reportWebVitals();
