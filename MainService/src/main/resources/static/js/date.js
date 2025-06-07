const d = new Date();
const day = d.getDay();
const dayNames = ["Niedziela","Poniedziałek","Wtorek","Środa","Czwartek","Piątek","Sobota"];
const dayName = dayNames[day];

const dayNameSpan = document.getElementById('day-name');
dayNameSpan.innerText = dayName;

const dateDay = d.getDate();
const dateMonth = d.getMonth() + 1;
const dateYear = d.getFullYear();

let dayPrefix = '0';
let monthPrefix = '0';

if (dateDay > 9)
    dayPrefix = '';

if (monthPrefix > 9)
    monthPrefix = '';

const currentDate = `${dayPrefix}${dateDay}.${monthPrefix}${dateMonth}.${dateYear}`;

const dateSpan = document.getElementById('date');
date.innerText = currentDate;