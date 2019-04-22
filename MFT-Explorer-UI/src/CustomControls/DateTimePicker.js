import React from "react";
import PropTypes from "prop-types";
import { withStyles } from "@material-ui/core/styles";
import TextField from "@material-ui/core/TextField";

const styles = theme => ({
  container: {
    display: "flex",
    flexWrap: "wrap"
  },
  textField: {
    marginLeft: theme.spacing.unit,
    marginRight: theme.spacing.unit,
  }
});

function DateAndTimePickers(props) {

  const { classes, label, capturedValue, defaultDateTime } = props;
  const valcapturedValue = event => {
    capturedValue(event.target.value)
  };

  return (
    <form className={classes.container} noValidate>
      <TextField
        fullWidth        
        id="datetime-local"
        label={label}
        type="datetime-local"
        defaultValue={defaultDateTime}
        onChange={valcapturedValue}
        className={classes.textField}
        InputLabelProps={{
          shrink: true
        }}
      />
    </form>
  );
}

// DateAndTimePickers.propTypes = {
//   classes: PropTypes.object.isRequired
// };

export default withStyles(styles)(DateAndTimePickers);
