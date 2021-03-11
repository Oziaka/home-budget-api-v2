package pl.wallet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import pl.user.User;
import pl.wallet.transaction.model.Transaction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@EqualsAndHashCode(exclude = {"owner", "users"})
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "wallet")
public class Wallet {


   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "wallet_id")
   @Id
   private Long id;

   @Column(nullable = false)
   private String name;

   @Column(nullable = false)
   private BigDecimal balance;

   @OneToMany(mappedBy = "wallet")
   private List<Transaction> transactions;

   @ManyToMany(mappedBy = "wallets")
   @JsonIgnoreProperties
   private Set<User> users;

   @OneToOne
   private User owner;

   public void addTransaction(Transaction transaction) {
      this.balance = transaction.getCategory().getType().countBalance(this, transaction);
   }

   public void removeTransaction(Transaction transaction) {
      this.balance = transaction.getCategory().getType().undoCountBalance(this, transaction);
   }

   public void addUser(User user) {
      if (users == null)
         users = new HashSet<>(Collections.singleton(user));
      users.add(user);
   }

   public void removeUser(User user) {
      this.users = this.users.stream().filter(u -> !u.equals(user)).collect(Collectors.toSet());
   }


   @Builder
   public Wallet(String name, BigDecimal balance, List<Transaction> transactions, Set<User> users, User owner) {
      this.name = name;
      this.balance = balance;
      this.transactions = transactions;
      this.users = users;
      this.owner = owner;
   }
}
