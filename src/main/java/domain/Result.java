package domain;

import java.io.Serializable;
import java.util.Objects;
import java.util.Vector;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Result implements Serializable {

	@XmlID
	@Id
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer id;



	@OneToOne
	private Question question;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Vector<Bet> bets = new Vector<>();
	@OneToOne
	@XmlIDREF
	private Event event;
	private int possibleResult;
	private boolean finalResult;
	private boolean hasBeenProcessed;



	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}
	public Vector<Bet> getBets() {
		return bets;
	}

	private float fee;

	public Result() {
		finalResult = false;
	}

	public Result(int r){
		finalResult = false;
		possibleResult = r;
	}
	public Result(int r, float f) {
		this.fee = f;
		this.possibleResult = r;
		this.finalResult = false;
		
	}
	public Result(int r, float f,Question q) {
		this.fee = f;
		this.possibleResult = r;
		this.finalResult = false;
		this.question=q;
		this.event=q.getEvent();

	}

	public boolean isFinalResult() {
		return finalResult;
	}

	public void setFinalResult(boolean finalResult) {
		this.finalResult = finalResult;
	}


	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @return the result
	 */
	public int getPossibleResult() {
		return possibleResult;
	}
	/**
	 * @return the fee
	 */
	public float getFee() {
		return fee;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @param result the result to set
	 */
	public void setPossibleResult(int result) {
		this.possibleResult = result;
	}
	/**
	 * @param fee the fee to set
	 */
	public void setFee(float fee) {
		this.fee = fee;
	}

	public void setBets(Vector<Bet> bets) {
		this.bets = bets;
	}

	/**
	 * This method adds a bet to a user
	 *
	 * @param bet to be added to the list of bets
	 * @return Bet
	 */
	public Bet addBet(Bet bet)  {
		bets.add(bet);
		return bet;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Result result = (Result) o;
		return Objects.equals(possibleResult, result.possibleResult);
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public boolean isHasBeenProcessed() {
		return hasBeenProcessed;
	}

	public void setHasBeenProcessed(boolean hasBeenProcessed) {
		this.hasBeenProcessed = hasBeenProcessed;
	}
}

