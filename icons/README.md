# Icons

|Name|Icon|
|---|---|
|bug-48.png|![bug-48](bug-48.png)|
|bug-prohibited-48.png|![bug-prohibited-48](bug-prohibited-48.png)|
|bug-96.png|![bug-96](bug-96.png)|
|bug-prohibited-96.png|![bug-prohibited-96](bug-prohibited-96.png)|
|closed-50.png|![closed-50](closed-50.png)|
|closed-100.png|![closed-100](closed-100.png)|
|documentation-48.png|![documentation-48](documentation-48.png)|
|documentation-96.png|![documentation-96](documentation-96.png)|
|epic-48.png|![epic-48](epic-48.png)|
|epic-96.png|![epic-96](epic-96.png)|
|improvement-50.png|![improvement-50](improvement-50.png)|
|improvement-100.png|![improvement-100](improvement-100.png)|
|story-50.png|![story-50](story-50.png)|
|story-100.png|![story-100](story-100.png)|
|task-48.png|![task-48](task-48.png)|
|task-done-48.png|![task-done-48](task-done-48.png)|
|task-48.gif|![task-48](task-48.gif)|
|task-96.png|![task-96](task-96.png)|
|task-done-96.png|![task-done-96](task-done-96.png)|
|task-96.gif|![task-96](task-96.gif)|
|priority-highest-48.png|![priority-highest-48](priority-highest-48.png)|
|priority-high-48.png|![priority-high-48](priority-high-48.png)|
|priority-medium-48.png|![priority-medium-48](priority-medium-48.png)|
|priority-low-48.png|![priority-low-48](priority-low-48.png)|
|priority-lowest-48.png|![priority-lowest-48](priority-lowest-48.png)|

## AddProhibitedSign.java

Утилита добавляющая поверх файла изображения (jpg/jpeg, png, gif, bmp/dib, wbmp) запрещающий знак красного цвета размером 60% от максимального значения width/height изображения.

```Shell
javac AddProhibitedSign.java # Компилирование java to class
java AddProhibitedSign $input_file $output_file # Использование
```

## DiamondExclamationGenerator.java

Утилита, которая принимает от пользователя размеры width/heght изображения, цвет заливки и выходное имя файла (по умолчанию в формате png) и затем рисует по центру изображения ромб размером 75% от указанных размеров со скругленными углами, однородно залитый указанным цветом и в самом центре белый восклицательный знак шрифтом 'Arial' размером минимум '18px', максимум 1/2 размера ромба с тенью.\
Все пользовательские настройки задаются в интерактивном режиме.

```Shell
javac DiamondExclamationGenerator.java # Компилирование java to class
java DiamondExclamationGenerator # Использование
```
