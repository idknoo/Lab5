package city;

/**
 * класс координат
 */
public class Coordinates {

        /**
         * Поле координаты x,
         */
        private float x;

        /**
         * Поле координаты y
         */
        private float y;

        public Coordinates() {}

        /**
         * Конструктор для задания координат x и y
         *
         * @param x - координата x
         * @param y - координата y
         */
        public Coordinates(float x, float y) {
            this.x = x;
            this.y = y;
        }

        /**
         * Метод для получения координаты y
         *
         * @return возвращает значение координаты y
         */
        public float getY() {
            return y;
        }

        /**
         * Метод для получения координаты x
         *
         * @return возвращает значение координаты x
         */
        public float getX() {
            return x;
        }

        /**
         * Метод для получения координат организации в строчной форме в формате "X = A Y = B"
         *
         * @return возвращает строку координат в формате "A B"
         */
        @Override
        public String toString() {
            return x +" "+ y;
        }

    }
