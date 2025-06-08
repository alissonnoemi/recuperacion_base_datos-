// Función para la barra de búsqueda (sin cambios, ya es JS puro)
        document.getElementById('search').addEventListener('keyup', function() {
            let input, filter, table, tr, td, i, txtValue;
            input = document.getElementById('search');
            filter = input.value.toUpperCase();
            table = document.getElementById('business-list-body');
            tr = table.getElementsByTagName('tr');

            for (i = 0; i < tr.length; i++) {
                // Buscamos en las columnas 1 (nombreNegocio) y 3 (emailProfesional)
                let found = false;
                // Columna Nombre del Negocio (índice 1)
                td = tr[i].getElementsByTagName('td')[1];
                if (td) {
                    txtValue = td.textContent || td.innerText;
                    if (txtValue.toUpperCase().indexOf(filter) > -1) {
                        found = true;
                    }
                }
                // Columna Email de Contacto (índice 3)
                if (!found) { // Solo si no se encontró en la columna anterior
                    td = tr[i].getElementsByTagName('td')[3];
                    if (td) {
                        txtValue = td.textContent || td.innerText;
                        if (txtValue.toUpperCase().indexOf(filter) > -1) {
                            found = true;
                        }
                    }
                }

                if (found) {
                    tr[i].style.display = "";
                } else {
                    tr[i].style.display = "none";
                }
            }
        });

        // Script para hacer que los mensajes flash desaparezcan
        document.addEventListener('DOMContentLoaded', function() {
            const flashMessages = document.querySelectorAll('.flash-message');
            flashMessages.forEach(msg => {
                // Ya la animación CSS hace el fadeOut, solo necesitamos eliminar el elemento del DOM al final
                msg.addEventListener('animationend', () => {
                    msg.remove();
                });
            });
        });