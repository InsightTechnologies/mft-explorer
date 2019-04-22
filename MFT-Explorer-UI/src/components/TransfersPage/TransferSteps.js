import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import Stepper from '@material-ui/core/Stepper';
import Step from '@material-ui/core/Step';
import StepLabel from '@material-ui/core/StepLabel';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';

const styles = {
	root: {
		width: '100%',
	},
	// button: {
	// 	marginRight: theme.spacing.unit,
	// },
	// instructions: {
	// 	marginTop: theme.spacing.unit,
	// 	marginBottom: theme.spacing.unit,
	// },
}

function getSteps() {
	return [
		'Source & Target QMs',
		'Name, Type & Agents',
		'Watch Patterns / Schedule',
		'Transfer conditions',
		'Additional options ',

	]
}

// function getStepContent(step) {
// 	const steps = getSteps()
// 	switch (step) {
// 		case 0:
// 		return steps[0]
// 		case 1:
// 		return steps[1]
// 		case 2:
// 		return steps[2]
// 		case 3:
// 		return steps[3]
// 		case 4:
// 		return steps[4]
// 		default:
// 		return 'Unknown step';
// 	}
// }

class TransferSteps extends React.Component {
	state = {
		activeStep: 0,
		skipped: new Set(),
	};
	
	getActiveStep = () => {
		return this.state.activeStep
	}

	isStepOptional = step => step === 10;
	
	handleNext = () => {
		const { activeStep } = this.state;
		let { skipped } = this.state;
		if (this.isStepSkipped(activeStep)) {
			skipped = new Set(skipped.values());
			skipped.delete(activeStep);
		}
		this.setState({
			activeStep: activeStep + 1,
			skipped,
		});
		return activeStep+1
	};
	
	handleBack = () => {
		const { activeStep } = this.state;
		this.setState(state => ({
			activeStep: state.activeStep - 1,
		}));
		return activeStep-1
	};
	
	handleSkip = () => {
		const { activeStep } = this.state;
		if (!this.isStepOptional(activeStep)) {
			// You probably want to guard against something like this,
			// it should never occur unless someone's actively trying to break something.
			throw new Error("You can't skip a step that isn't optional.");
		}
		
		this.setState(state => {
			const skipped = new Set(state.skipped.values());
			skipped.add(activeStep);
			return {
				activeStep: state.activeStep + 1,
				skipped,
			};
		});
	};
	
	handleReset = () => {
		this.setState({
			activeStep: 0,
		});
	};
	
	isStepSkipped(step) {
		return this.state.skipped.has(step);
	}
	
	render() {
		// const { classes } = this.props;
		const steps = getSteps();
		const { activeStep } = this.state;
		
		return (
			<div style={styles.root}>
			<Stepper activeStep={activeStep} alternativeLabel>
			{steps.map((label, index) => {
				const props = {};
				const labelProps = {};
				if (this.isStepOptional(index)) {
					labelProps.optional = <Typography variant="caption">Optional</Typography>;
				}
				if (this.isStepSkipped(index)) {
					props.completed = false;
				}
				return (
					<Step key={label} {...props}>
					<StepLabel {...labelProps}>{label}</StepLabel>
					</Step>
					);
				})}
				</Stepper>
				<div style={{display:'none'}}>
				{activeStep === steps.length ? (
					<div>
					{/* <Typography style={styles.instructions}>
					All steps completed - you&apos;re finished
					</Typography> */}
					<Button onClick={this.handleReset} style={styles.button}>
					Reset
					</Button>
					</div>
					) : (
						<div>
						{/* <Typography style={styles.instructions}>{getStepContent(activeStep)}</Typography> */}
						<div>
						<Button
						disabled={activeStep === 0}
						onClick={this.handleBack}
						style={styles.button}
						>
						Back
						</Button>
						{this.isStepOptional(activeStep) && (
							<Button
							variant="contained"
							color="primary"
							onClick={this.handleSkip}
							style={styles.button}
							>
							Skip
							</Button>
							)}
							<Button
							variant="contained"
							color="primary"
							onClick={this.handleNext}
							style={styles.button}
							>
							{activeStep === steps.length - 1 ? 'Finish' : 'Next'}
							</Button>
							</div>
							</div>
							)}
							</div>
			</div>
		);
	}
}

// TransferSteps.propTypes = {
// 	classes: PropTypes.object,
// };

export default TransferSteps;