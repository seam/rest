var firstTask = 0;
var maxResults = 5;

$(document).ready(function() {
//	 next and previous buttons
    $('.next').click(function(event) {
        event.preventDefault();
        firstTask += maxResults;
        printTasks();
    });
    $('.previous').click(function(event) {
        event.preventDefault();
        firstTask -= maxResults;
        printTasks();
    });

    printTasks();
});

/**
 * Load tasks and render them on the page
 */
function printTasks() {
    $('#tasks tbody').empty();
    getTasks("resolved", firstTask, maxResults, function(data) {
        for (key in data) {
            addTask(data[key]);
        }

        // pagination handling
        if (data.length == maxResults) {
            $('.next').show();
        } else {
            $('.next').hide();
        }
        if (firstTask >= maxResults) {
            $('.previous').show();
        } else {
            $('.previous').hide();
        }
        initialized();
    });
}

function addTask(task) {
    var parent = $('#tasks tbody');
    var nameCell = $('<td/>').addClass('name').text(task.name);
    var updatedCell = $('<td/>').addClass('updated').text($.format.date(task.updated, "dd/MM/yyyy hh:mm:ss"));
    var undoButton = $('<img/>').attr('src', 'img/task-undo.png').attr('title', 'Undo this task').click(function(event) {
        event.preventDefault();
        task.resolved = false;
        putTask(task, function() {
            $('#' + task.id).remove();
        });
    });
    var deleteButton = $('<img/>').attr('src', 'img/task-delete.png').attr('title', 'Delete this task').click(function(event) {
        event.preventDefault();
        deleteTask(task, function() {
            $('#' + task.id).remove();
        });
    });
    var buttonCell = $('<td/>').append(undoButton).append(deleteButton);
    $('<tr/>').attr('id', task.id).append(buttonCell).append(nameCell).append(updatedCell).appendTo(parent);
}
