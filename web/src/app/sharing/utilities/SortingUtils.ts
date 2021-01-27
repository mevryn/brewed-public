export class SortingUtils {
    public static alphabetically(ascending) {
        return function(a, b) {

            // equal items sort equally
            if (a === b) {
                return 0;
            }
            // nulls sort after anything else
            else if (a === null) {
                return 1;
            } else if (b === null) {
                return -1;
            }
            // otherwise, if we're ascending, lowest sorts first
            else if (ascending) {
                return a < b ? -1 : 1;
            }
            // if descending, highest sorts first
            else {
                return a < b ? 1 : -1;
            }

        };

    }
}
