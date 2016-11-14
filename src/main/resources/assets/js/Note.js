var Note = React.createClass({
    displayName: 'Note',

    getInitialState: function () {
        return { editing: false };
    },
    componentWillMount: function () {
        this.style = {
            right: this.randomBetween(0, window.innerWidth - 150) + 'px',
            top: this.randomBetween(0, window.innerHeight - 150) + 'px',
            transform: 'rotate(' + this.randomBetween(-15, 15) + 'deg)'
        };
    },
    componentDidMount: function () {
        $(this.getDOMNode()).draggable();
    },
    randomBetween: function (min, max) {
        return min + Math.ceil(Math.random() * max);
    },
    edit: function () {
        this.setState({ editing: true });
    },
    save: function () {
        var context = this;
        var noteText = this.refs.newText.getDOMNode().value;
        var index = this.props.index;

        var data = {
            subject: index,
            text: noteText
        };

        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            type: "POST",
            url: "/notes/addnote",
            data: JSON.stringify(data),
            success: function(result) {
                context.props.onChange(noteText, index);
            }
        });

        this.setState({ editing: false });
    },
    remove: function () {
        this.props.onRemove(this.props.index);
    },
    renderDisplay: function () {
        return React.createElement(
            'div',
            { className: 'note',
                style: this.style },
            React.createElement(
                'p',
                null,
                this.props.children
            ),
            React.createElement(
                'span',
                null,
                React.createElement('button', { onClick: this.edit,
                    className: 'btn btn-primary glyphicon glyphicon-pencil' }),
                React.createElement('button', { onClick: this.remove,
                    className: 'btn btn-danger glyphicon glyphicon-trash' })
            )
        );
    },
    renderForm: function () {
        return React.createElement(
            'div',
            { className: 'note', style: this.style },
            React.createElement('textarea', { ref: 'newText', defaultValue: this.props.children,
                className: 'form-control' }),
            React.createElement('button', { onClick: this.save, className: 'btn btn-success btn-sm glyphicon glyphicon-floppy-disk' })
        );
    },
    render: function () {
        if (this.state.editing) {
            return this.renderForm();
        } else {
            return this.renderDisplay();
        }
    }
});

var Board = React.createClass({
    displayName: 'Board',

    propTypes: {
        count: function (props, propName) {
            if (typeof props[propName] !== "number") {
                return new Error('The count property must be a number');
            }
            if (props[propName] > 100) {
                return new Error("Creating " + props[propName] + " notes is ridiculous");
            }
        }
    },
    getInitialState: function () {
        return {
            notes: []
        };
    },
    //TODO kuka
    nextId: function () {
        this.uniqueId = this.uniqueId || 0;
        return this.uniqueId++;
    },
    componentWillMount: function () {
        var self = this;
        if (this.props.count) {
            $.getJSON("/notes/list", function (results) {
                    results.forEach(function (note) {
                    self.add(note.id, note.text);
               });
            });
        }
    },
    add: function (noteId, noteText) {
        var arr = this.state.notes;
        arr.push({
            id: noteId,
            note: noteText
        });
        this.setState({ notes: arr });
    },
    update: function (newText, i) {
        var arr = this.state.notes;
        arr[i].note = newText;
        this.setState({ notes: arr });
    },
    remove: function (i) {
        var arr = this.state.notes;
        arr.splice(i, 1);
        this.setState({ notes: arr });
    },
    eachNote: function (note, i) {
        return React.createElement(
            Note,
            {   key: note.id,
                index: i,
                onChange: this.update,
                onRemove: this.remove
            },
            note.note
        );
    },
    render: function () {
        return React.createElement(
            'div',
            { className: 'board' },
            this.state.notes.map(this.eachNote),
            React.createElement('button', { className: 'btn btn-sm btn-success glyphicon glyphicon-plus',
                onClick: this.add.bind(null, "New Note") })
        );
    }
});

React.render(React.createElement(Board, { count: 50 }), document.getElementById('react-container'));